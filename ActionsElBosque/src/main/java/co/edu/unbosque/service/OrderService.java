package co.edu.unbosque.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import co.edu.unbosque.config.AlpacaConfig;
import co.edu.unbosque.model.request.OrderRequest;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final WebClient brokerClient;
    
    public OrderService(AlpacaConfig config) {
        this.brokerClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultHeader("Authorization", config.getTokenAuth())
                .build();
    }

    public Mono<String> placeOrder(String accountId, OrderRequest orderRequest) {
        return brokerClient.post()
                .uri("/v1/trading/accounts/{account_id}/orders", accountId)
                .bodyValue(orderRequest)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    return Mono.error(new RuntimeException("Error placing order: " + e.getMessage()));
                });
    }
}