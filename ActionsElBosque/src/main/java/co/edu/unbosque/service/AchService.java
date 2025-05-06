package co.edu.unbosque.service;

import co.edu.unbosque.config.AlpacaConfig;
import co.edu.unbosque.model.request.AchRelationshipRequest;
import co.edu.unbosque.model.request.AchTransferRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AchService {

    private final WebClient brokerClient; 
    private final ObjectMapper objectMapper;

    public AchService(AlpacaConfig config) {
                
        this.brokerClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultHeader("Authorization", config.getTokenAuth())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(50 * 1024 * 1024))
                .build();

        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public Mono<Object> createAchRelationship(String accountId, AchRelationshipRequest request) {
        return brokerClient.post()
                .uri("/v1/accounts/{account_id}/ach_relationships", accountId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, Object.class);
                    } catch (Exception e) {
                        return Map.of("error", "Error al parsear JSON", "details", e.getMessage());
                    }
                })
                .onErrorResume(e -> Mono.just(Map.of("error", "Error en la solicitud", "details", e.getMessage())));
    }
    public Mono<Object> createAchTransfer(String accountId, AchTransferRequest request) {
        return brokerClient.post()
                .uri("/v1/accounts/{account_id}/transfers", accountId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, Object.class);
                    } catch (Exception e) {
                        return Map.of("error", "Error al parsear JSON", "details", e.getMessage());
                    }
                })
                .onErrorResume(e -> Mono.just(Map.of("error", "Error en la solicitud", "details", e.getMessage())));
    }
}