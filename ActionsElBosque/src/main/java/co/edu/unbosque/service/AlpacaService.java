package co.edu.unbosque.service;

import co.edu.unbosque.config.AlpacaConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AlpacaService {

    private final WebClient tradingClient;
    private final ObjectMapper objectMapper;

    public AlpacaService(AlpacaConfig config) {
        this.tradingClient = WebClient.builder()
                .baseUrl(config.getBaseUrl()) 
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



}