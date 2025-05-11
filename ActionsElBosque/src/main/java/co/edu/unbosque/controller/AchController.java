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

import co.edu.unbosque.model.request.AchRelationshipRequest;
import co.edu.unbosque.model.request.AchTransferRequest;
import co.edu.unbosque.service.AchService;
import co.edu.unbosque.service.AccountAlpService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alpaca")
@CrossOrigin(origins = { "http://localhost:8085", "http://localhost:4200" })
public class AchController {

    @Autowired
    private AchService achServ;
    
    @PostMapping("/accounts/{account_id}/ach_relationships")
    public ResponseEntity<Object> createAchRelationship(@PathVariable("account_id") String accountId,
            @RequestBody AchRelationshipRequest request) {
        Object response = achServ.createAchRelationship(accountId, request).block();
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/accounts/{account_id}/transfers")
    public ResponseEntity<Object> createAchTransfer(@PathVariable("account_id") String accountId,
            @RequestBody AchTransferRequest request) {
        Object response = achServ.createAchTransfer(accountId, request).block();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/accounts/{account_id}/ach-relationships")
    public Mono<ResponseEntity<List<String>>> getAchRelationshipsIds(@PathVariable("account_id") String accountId) {
        return achServ.getAchRelationshipsIds(accountId)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                                .body(Collections.emptyList())
                ));
    }
    
}