package com.example.recursos.WebClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CursoClient {

    private final WebClient webClient;

    public CursoClient(@Value("${curso-service.url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Map<String, Object> obtenerCursoPorId(Long id) {
        return this.webClient.get()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response ->
                response.bodyToMono(String.class)
                    .map(msg -> new RuntimeException("Curso no encontrado"))
            )
            .bodyToMono(Map.class)
            .block();
    }
}





