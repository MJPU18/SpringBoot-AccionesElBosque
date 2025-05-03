package co.edu.unbosque.service;

import co.edu.unbosque.config.AlpacaConfig;
import co.edu.unbosque.model.UserDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AccountAlpService {

    private final WebClient tradingClient;
    private final WebClient brokerClient; // Nuevo cliente para la API broker
    private final ObjectMapper objectMapper;

    public AccountAlpService(AlpacaConfig config) {
        this.tradingClient = WebClient.builder()
                .baseUrl(config.getBaseUrl()) 
                .defaultHeader("Authorization", config.getTokenAuth())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(50 * 1024 * 1024))
                .build();
                
        // Nuevo cliente para la API broker con diferente URL base
        this.brokerClient = WebClient.builder()
                .baseUrl("https://broker-api.sandbox.alpaca.markets")
                .defaultHeader("Authorization", config.getTokenAuth())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(50 * 1024 * 1024))
                .build();

        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public Object getAccountAsObject() {
        String response = tradingClient.get()
                .uri("/v1/accounts")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            return objectMapper.readValue(response, Object.class);
        } catch (Exception e) {
            return Map.of("error", "Error al parsear JSON", "details", e.getMessage());
        }
    }
    
    public Mono<Object> createAccount(UserDTO user) {
        return brokerClient.post()
                .uri("/v1/accounts")
                .bodyValue(user)
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