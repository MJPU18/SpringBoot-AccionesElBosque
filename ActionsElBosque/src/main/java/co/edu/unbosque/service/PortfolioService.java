package co.edu.unbosque.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import co.edu.unbosque.config.AlpacaConfig;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioService {
    private final WebClient brokerClient;
    private final ObjectMapper objectMapper;
    
    public PortfolioService(AlpacaConfig config) {
        this.brokerClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultHeader("Authorization", config.getTokenAuth())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(50 * 1024 * 1024))
                .build();

        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Mono<Map<String, String>> getAccountValues(String accountId) {
        return brokerClient.get()
                .uri("/v1/trading/accounts/{account_id}/account", accountId)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        // Parse the full response
                        Map<String, Object> fullResponse = objectMapper.readValue(
                                response,
                                new TypeReference<Map<String, Object>>() {});
                        
                        // Create a new map with only the required fields
                        Map<String, String> simplifiedResponse = new HashMap<>();
                        simplifiedResponse.put("portfolio_value", fullResponse.get("portfolio_value").toString());
                        simplifiedResponse.put("buying_power", fullResponse.get("buying_power").toString());
                        
                        return Mono.just(simplifiedResponse);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
    public Mono<List<Map<String, Object>>> getFillActivitiesByAccountId(String accountId) {
        return brokerClient.get()
                .uri("/v1/accounts/activities/FILL")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        // Parsear la respuesta JSON a una lista de mapas
                        List<Map<String, Object>> allActivities = objectMapper.readValue(
                                response, 
                                new TypeReference<List<Map<String, Object>>>() {});

                        // Filtrar las actividades por account_id
                        List<Map<String, Object>> filteredActivities = allActivities.stream()
                                .filter(activity -> accountId.equals(activity.get("account_id")))
                                .collect(Collectors.toList());

                        return Mono.just(filteredActivities);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
    public Mono<List<Map<String, Object>>> getAcceptedActivitiesByAccountId(String accountId) {
        return brokerClient.get()
                .uri("/v1/trading/accounts/{account_id}/orders", accountId)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        // Parse the JSON response to a list of maps
                        List<Map<String, Object>> orders = objectMapper.readValue(
                                response, 
                                new TypeReference<List<Map<String, Object>>>() {});
                        
                        return Mono.just(orders);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
    
    public Mono<List<Map<String, Object>>> getTransferActivitiesByAccountId(String accountId) {
        return brokerClient.get()
                .uri("/v1/accounts/activities/TRANS")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        // Parsear la respuesta JSON a una lista de mapas
                        List<Map<String, Object>> allActivities = objectMapper.readValue(
                                response, 
                                new TypeReference<List<Map<String, Object>>>() {});

                        // Filtrar las actividades por account_id
                        List<Map<String, Object>> filteredActivities = allActivities.stream()
                                .filter(activity -> accountId.equals(activity.get("account_id")))
                                .collect(Collectors.toList());

                        return Mono.just(filteredActivities);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}