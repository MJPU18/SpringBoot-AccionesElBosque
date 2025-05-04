package co.edu.unbosque.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.request.AchRelationshipRequest;
import co.edu.unbosque.model.request.AchTransferRequest;
import co.edu.unbosque.service.AchService;

@RestController
@RequestMapping("/alpaca")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "http://localhost:4200", "*" })
public class AchController {

	@Autowired
	private AchService achServ;

	@PostMapping("/accounts/{account_id}/ach_relationships")
	public ResponseEntity<Object> createAchRelationship(@PathVariable("account_id") String accountId,
			@RequestBody AchRelationshipRequest request) {
		Object response = achServ.createAchRelationship(accountId, request).block();
		return ResponseEntity.ok(response);
	}
	
	// Add this method to AchRelationshipController.java
	@PostMapping("/accounts/{account_id}/transfers")
	public ResponseEntity<Object> createAchTransfer(@PathVariable("account_id") String accountId,
	        @RequestBody AchTransferRequest request) {
	    Object response = achServ.createAchTransfer(accountId, request).block();
	    return ResponseEntity.ok(response);
	}

}