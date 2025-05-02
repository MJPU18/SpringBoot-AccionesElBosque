package co.edu.unbosque.controller;

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
		// TODO Auto-generated constructor stub
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
	
	@GetMapping(path = "/check")
	public ResponseEntity<String> checkAccount(@RequestParam String email, @RequestParam String password){
		if(userServ.verifyAccount(email, password)) {
			return new ResponseEntity<String>("Encontrado",HttpStatus.FOUND);
		}
		return new ResponseEntity<String>("No encontrado",HttpStatus.NOT_FOUND);
	}

}
