package co.edu.unbosque.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.request.UserAlpRequest;
import co.edu.unbosque.model.request.UserRequest;
import co.edu.unbosque.service.AccountAlpService;
import co.edu.unbosque.service.UserActivityService;
import co.edu.unbosque.service.UserService;
import co.edu.unbosque.util.EmailSender;
import co.edu.unbosque.util.UserRequestMapper;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alpaca")
@CrossOrigin(origins = { "http://localhost:8085", "http://localhost:4200" })
public class AccountAlpController {

	@Autowired
	private UserService userServ;
	@Autowired
	private AccountAlpService accountServ;
	@Autowired
	private UserActivityService userActServ;

	@GetMapping("/accounts/getall")
	public ResponseEntity<Object> getAccounts() {
		Object result = accountServ.getAccounts();
		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/accounts/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createAccount(@RequestBody UserRequest request) {
		UserAlpRequest userAlp = UserRequestMapper.toUserAlpRequest(request);
		Object response = accountServ.createAccount(userAlp).block();

		if (response instanceof Map) {
			Map<?, ?> responseMap = (Map<?, ?>) response;

			if (responseMap.containsKey("error")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
			if (responseMap.containsKey("id")) {
				User newUser = new User();
				newUser.setFirstName(request.getIdentity().getGiven_name());
				newUser.setLastName(request.getIdentity().getFamily_name());
				newUser.setEmail(request.getContact().getEmail_address());
				newUser.setPhone(request.getContact().getPhone_number());
				newUser.setCardId(request.getIdentity().getTax_id());
				newUser.setPassword(request.getPassword());
				newUser.setAdministrator(false);

				String validate = userServ.validateUser(newUser);
				if (!validate.isEmpty()) {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Usuario Inaceptable: " + validate);
				}

				String accountId = (String) responseMap.get("id");
				newUser.setAlpacaUserId(accountId);
				User savedUser = userServ.create(newUser);

				userActServ.logUserActivity(savedUser.getUserId(), "ACCOUNT_CREATION",
						"Usuario creado: " + savedUser.getEmail());
				String htmlWelcome = EmailSender.buildWelcomeEmail(newUser.getFirstName()+" "+newUser.getLastName());
				EmailSender.sendEmail(newUser.getEmail(), "¡Bienvenida a Acciones ElBosque!", htmlWelcome);

				return ResponseEntity.ok(response);
			}
		}
		return ResponseEntity.internalServerError().body("Respuesta inesperada del servicio Alpaca");
	}

	@GetMapping(path = "/check")
	public ResponseEntity<Object> checkAccount(@RequestParam String email, @RequestParam String password) {
		User user = userServ.verifyAccount(email, password);
		if (user != null) {
			Object account = accountServ.getAccountById(user.getAlpacaUserId());
			userActServ.logUserActivity(user.getUserId(), "LOGIN", "Usuario inicio sesion: " + user.getEmail());
			return new ResponseEntity<Object>(account, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("No encontrado", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/accounts/{id}/trading-details")
	public Mono<ResponseEntity<Object>> getAccountTradingDetails(@PathVariable String id) {
		return accountServ.getAccountTradingDetails(id).map(response -> {
			if (response instanceof Map && ((Map<?, ?>) response).containsKey("error")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
			return ResponseEntity.ok(response);
		});
	}

	@GetMapping("/accounts/{account_id}/portfolio/history")
	public Mono<ResponseEntity<Object>> getPortfolioHistoryById(@PathVariable("account_id") String accountId) {

		return accountServ.getPortfolioHistoryById(accountId).map(response -> {
			if (response instanceof Map && ((Map<?, ?>) response).containsKey("error")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
			return ResponseEntity.ok(response);
		});
	}

	@PostMapping("/accounts/{accountId}/close")
	public Mono<ResponseEntity<Object>> closeAccount(@PathVariable String accountId) {
		return accountServ.closeAccount(accountId).map(response -> ResponseEntity.ok().body(response))
				.defaultIfEmpty(ResponseEntity.ok().build());
	}

}