package com.example.asistencia.WebClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InscripcionClient {
    private final WebClient webClient;

    public InscripcionClient(@Value("${inscripcion-service.url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Map<String, Object> obtenerInscripcionPorId(Long id) {
        return this.webClient.get()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response ->
                response.bodyToMono(String.class)
                    .map(msg -> new RuntimeException("Inscripción no encontrada"))
            )
            .bodyToMono(Map.class)
            .block();
    }
}
