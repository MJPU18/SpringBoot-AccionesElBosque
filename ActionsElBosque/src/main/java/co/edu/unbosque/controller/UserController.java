package co.edu.unbosque.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.model.User;
import co.edu.unbosque.model.request.EmailRequest;
import co.edu.unbosque.service.UserService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8085", "*"})
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
	
	@PostMapping(path = "/getalpacaid", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> getAlpacaUserIdByEmail(@RequestBody EmailRequest emailRequest) {
	    if (emailRequest.getEmail() == null || emailRequest.getEmail().trim().isEmpty()) {
	        return new ResponseEntity<>(Map.of("error", "El email no puede estar vacío"), HttpStatus.BAD_REQUEST);
	    }
	    
	    try {
	        String alpacaUserId = userServ.getAlpacaUserIdByEmail(emailRequest.getEmail());
	        if (alpacaUserId != null) {
	            return new ResponseEntity<>(Map.of("alpacaUserId", alpacaUserId), HttpStatus.OK);
	        }
	        return new ResponseEntity<>(Map.of("error", "No se encontró usuario con el email proporcionado"), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>(Map.of("error", "Error al buscar el usuario: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
