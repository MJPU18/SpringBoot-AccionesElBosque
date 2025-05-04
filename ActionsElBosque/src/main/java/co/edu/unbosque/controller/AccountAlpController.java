package co.edu.unbosque.controller;


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
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.UserDTO;
import co.edu.unbosque.service.AccountAlpService;
import co.edu.unbosque.service.UserService;

@RestController
@RequestMapping("/alpaca")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081","http://localhost:4200", "*" })
public class AccountAlpController {
	
    private final AccountAlpService alpacaService;
    @Autowired
	private UserService userServ;
    
    public AccountAlpController(AccountAlpService alpacaService) {
        this.alpacaService = alpacaService;
    }
    
    @GetMapping("/accounts/getall")
    public ResponseEntity<Object> getAccount() {
        Object result = alpacaService.getAccountAsObject();
        return ResponseEntity.ok(result);
    }
    
    @PostMapping(path="/accounts/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAccount(@RequestBody UserDTO request) {
    	
        Object response = alpacaService.createAccount(request).block();
        
       
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
                newUser.setPassword("defaultPassword"); 
                newUser.setAdministrator(false);
                
                String validate = userServ.validateUser(newUser);
                if (!validate.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body("Usuario Inaceptable: " + validate);
                }
                
                String accountId = (String) responseMap.get("id");
                newUser.setAlpacaUserId(accountId);
                userServ.create(newUser);
                
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.internalServerError().body("Respuesta inesperada del servicio Alpaca");
    }
    
}