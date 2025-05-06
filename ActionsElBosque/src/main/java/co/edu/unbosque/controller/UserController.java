package co.edu.unbosque.controller;

import java.io.ObjectInputFilter.Config;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import co.edu.unbosque.config.AlpacaConfig;
import co.edu.unbosque.model.User;
import co.edu.unbosque.service.UserService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "*" })
@Transactional
public class UserController {
	
	@Autowired
	private UserService userServ;
	
	
	public UserController() {
		
	}
	
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createUser(@RequestBody User newUser){
		newUser.setUserId(null);
		String validate=userServ.validateUser(newUser);
		if(!validate.isEmpty()) {
			return new ResponseEntity<String>("Usuario Inaceptable"+validate,HttpStatus.NOT_ACCEPTABLE);
		}
		userServ.create(newUser);
		return new ResponseEntity<String>("Usuario creado exitosamente.",HttpStatus.CREATED);
		
	}
	
//	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String> createUser(@RequestBody User newUser) {
//	    newUser.setUserId(null);
//	    String validate = userServ.validateUser(newUser);
//	    if (!validate.isEmpty()) {
//	        return new ResponseEntity<String>("Usuario Inaceptable" + validate, HttpStatus.NOT_ACCEPTABLE);
//	    }
//
//	    // Primero creamos el usuario en nuestro sistema
//	    userServ.create(newUser);
//	    
//	    try {
//	        // Luego creamos la cuenta en Alpaca
//	        String alpacaResponse = createAlpacaAccount(newUser);
//	        return new ResponseEntity<String>("Usuario creado exitosamente. Respuesta Alpaca: " + alpacaResponse, HttpStatus.CREATED);
//	    } catch (Exception e) {
//	        // Si falla Alpaca, podrías querer revertir la creación del usuario
//	        return new ResponseEntity<String>("Usuario creado pero falló creación en Alpaca: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//
//	private String createAlpacaAccount(User user) {
//	    AlpacaConfig config;
//	    WebClient webClient = WebClient.builder()
//	            .baseUrl("https://broker-api.sandbox.alpaca.markets/v1")
//	            .defaultHeader("Authorization", config.getTokenAuth() )
//	            .build();
//	    
//	    // Mapear tu User al formato que espera Alpaca
//	    // (Aquí necesitarás adaptar según cómo sean tus campos de User)
//	    Map<String, Object> alpacaAccountRequest = Map.of(
//	        "contact", Map.of(
//	            "email_address", user.getEmail(),
//	            "phone_number", user.getPhoneNumber(),
//	            "street_address", List.of(user.getAddress()),
//	            "city", user.getCity(),
//	            "postal_code", "",
//	            "state", user.getState()
//	        ),
//	        "identity", Map.of(
//	            "given_name", user.getFirstName(),
//	            "family_name", user.getLastName(),
//	            "date_of_birth", "1990-01-01", // Deberías tener este campo en User
//	            "tax_id_type", "COL_CC",
//	            "tax_id", user.getCardId(), // Asumiendo que tienes este campo
//	            "country_of_citizenship", "COL",
//	            "country_of_birth", "COL",
//	            "country_of_tax_residence", "COL",
//	            "funding_source", List.of("employment_income"),
//	            "annual_income_min", "10000",
//	            "annual_income_max", "10000",
//	            "total_net_worth_min", "10000",
//	            "total_net_worth_max", "10000",
//	            "liquid_net_worth_min", "10000",
//	            "liquid_net_worth_max", "10000",
//	            "liquidity_needs", "does_not_matter",
//	            "investment_experience_with_stocks", "good",
//	            "investment_experience_with_options", "good",
//	            "risk_tolerance", "conservative",
//	            "investment_objective", "market_speculation",
//	            "investment_time_horizon", "more_than_10_years",
//	            "marital_status", "MARRIED",
//	            "number_of_dependents", 1
//	        ),
//	        "disclosures", Map.of(
//	            "is_control_person", false,
//	            "is_affiliated_exchange_or_finra", false,
//	            "is_politically_exposed", false,
//	            "immediate_family_exposed", false
//	        ),
//	        "agreements", List.of(
//	            Map.of(
//	                "agreement", "customer_agreement",
//	                "signed_at", Instant.now().toString(),
//	                "ip_address", "127.0.0.1" 
//	            )
//	        )
//	    );
//	    
//	    // Hacer la petición a Alpaca
//	    return webClient.post()
//	            .uri("/accounts")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .bodyValue(alpacaAccountRequest)
//	            .retrieve()
//	            .bodyToMono(String.class)
//	            .block();
//	}

}
