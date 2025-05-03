package co.edu.unbosque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.service.AlpacaService;

@RestController
@RequestMapping("/alpaca")
@CrossOrigin(origins = "*") // Ampliado para permitir cualquier origen durante el desarrollo
public class AlpacaController {
    private final AlpacaService alpacaService;
    
    public AlpacaController(AlpacaService alpacaService) {
        this.alpacaService = alpacaService;
    }
    
    @GetMapping("/accounts")
    public ResponseEntity<Object> getAccount() {
        Object result = alpacaService.getAccountAsObject();
        return ResponseEntity.ok(result);
    }
}