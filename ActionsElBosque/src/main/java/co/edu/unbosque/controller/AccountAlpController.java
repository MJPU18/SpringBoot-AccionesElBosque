package co.edu.unbosque.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.UserDTO;
import co.edu.unbosque.service.AccountAlpService;

@RestController
@RequestMapping("/alpaca")
@CrossOrigin(origins = "*") // Ampliado para permitir cualquier origen durante el desarrollo
public class AccountAlpController {
    private final AccountAlpService alpacaService;
    
    public AccountAlpController(AccountAlpService alpacaService) {
        this.alpacaService = alpacaService;
    }
    
    @GetMapping("/accounts")
    public ResponseEntity<Object> getAccount() {
        Object result = alpacaService.getAccountAsObject();
        return ResponseEntity.ok(result);
    }
    @PostMapping("/accounts")
    public ResponseEntity<Object> createAccount(@RequestBody UserDTO request) {
        Object response = alpacaService.createAccount(request).block();
        return ResponseEntity.ok(response);
    }
}