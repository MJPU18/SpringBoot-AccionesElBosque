package co.edu.unbosque.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.service.PortfolioService;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/alpaca")
@CrossOrigin(origins = { "http://localhost:8085", "http://localhost:4200" })
public class PortfolioController {
	
	@Autowired
	private PortfolioService portServ;
	
    @GetMapping("/{accountId}/activities/FILL")
    public Mono<List<Map<String, Object>>> getFillActivitiesByAccountId(@PathVariable String accountId) {
        return portServ.getFillActivitiesByAccountId(accountId);
    }
    
    @GetMapping("/{accountId}/activities/TRANS")
    public Mono<List<Map<String, Object>>> getTransferActivitiesByAccountId(@PathVariable String accountId) {
        return portServ.getTransferActivitiesByAccountId(accountId);
    }
    @GetMapping("/{accountId}/Accepted")
    public Mono<List<Map<String, Object>>> getOrdersByAccountId(@PathVariable String accountId) {
        return portServ.getAcceptedActivitiesByAccountId(accountId);
    }
	
}
