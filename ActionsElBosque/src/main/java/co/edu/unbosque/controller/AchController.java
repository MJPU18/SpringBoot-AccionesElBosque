package co.edu.unbosque.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.request.AchRelationshipRequest;
import co.edu.unbosque.model.request.AchTransferRequest;
import co.edu.unbosque.service.AchService;
import co.edu.unbosque.service.UserActivityService;
import co.edu.unbosque.service.UserService;
import co.edu.unbosque.util.EmailSender;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alpaca")
@CrossOrigin(origins = { "http://localhost:8085", "http://localhost:4200", "*"  })
public class AchController {

	@Autowired
	private AchService achServ;
	@Autowired
	private UserService userServ;
	@Autowired
	private UserActivityService userActServ;

	@PostMapping("/accounts/{account_id}/ach_relationships")
	public ResponseEntity<Object> createAchRelationship(@PathVariable("account_id") String accountId,
			@RequestBody AchRelationshipRequest request) {
		User user = userServ.getUserByAlpacaId(accountId);
		if (user == null) {
			return ResponseEntity.badRequest().body("Usuario no encontrado para la cuenta Alpaca proporcionada");
		}
		Object response = achServ.createAchRelationship(accountId, request).block();
		if (response instanceof Map) {
			Map<?, ?> responseMap = (Map<?, ?>) response;

			if (responseMap.containsKey("error")) {
				return ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT).body(response);
			}

			String action = "CREATION_RELATION_ACH";
			String details = String.format(
					"Se creó relación ACH para la cuenta %s. Nombre dueño: %s, Tipo cuenta: %s, Número cuenta: %s, Número ruta: %s",
					user.getEmail(), user.getFirstName() + " " + user.getLastName(), request.getBank_account_type(),
					request.getBank_account_number(), request.getBank_routing_number());
			userActServ.logUserActivity(user.getUserId(), action, details);
			String html = EmailSender.buildAchRelationshipEmail(user.getFirstName() + " " + user.getLastName(),
					request.getBank_account_type(), request.getBank_account_number(), request.getBank_routing_number());
			EmailSender.sendEmail(user.getEmail(), "Relación ACH creada en Acciones ElBosque", html);

		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/accounts/{account_id}/transfers")
	public ResponseEntity<Object> createAchTransfer(@PathVariable("account_id") String accountId,
			@RequestBody AchTransferRequest request) {
		User user = userServ.getUserByAlpacaId(accountId);
		if (user == null) {
			return ResponseEntity.badRequest().body("Usuario no encontrado para la cuenta Alpaca proporcionada");
		}

		Object response = achServ.createAchTransfer(accountId, request).block();
		if (response instanceof Map) {
			Map<?, ?> responseMap = (Map<?, ?>) response;

			if (responseMap.containsKey("error")) {
				return ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT).body(response);
			}
		}
		String action = "TRANSFER_ACH";
		String details = String.format(
				"Transferencia ACH para la cuenta %s. Tipo: %s, ID Relación: %s, Monto: %s, Dirección: %s", accountId,
				request.getTransfer_type(), request.getRelationship_id(), request.getAmount(), request.getDirection());

		userActServ.logUserActivity(user.getUserId(), action, details);
		String html = EmailSender.buildAchTransferEmail(user.getFirstName() + " " + user.getLastName(),
				request.getTransfer_type().toUpperCase(), request.getAmount(), request.getDirection());
		EmailSender.sendEmail(user.getEmail(), "Transferencia ACH completada", html);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/accounts/{account_id}/ach-relationships")
	public Mono<ResponseEntity<List<String>>> getAchRelationshipsIds(@PathVariable("account_id") String accountId) {
		return achServ.getAchRelationshipsIds(accountId).map(ResponseEntity::ok).onErrorResume(e -> Mono
				.just(ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Collections.emptyList())));
	}

}