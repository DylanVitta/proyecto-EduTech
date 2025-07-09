package com.example.evaluaciones.WebClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CursoClient {
    private final WebClient webClient;

    public CursoClient(@Value("${curso-service.url}") String cursoServidor){
        this.webClient = WebClient.builder().baseUrl(cursoServidor).build();
    }
    public Map<String, Object> obtenercursoId(Long id){
        return this.webClient.get()
        .uri("/{id}", id)
        .retrieve()
        .onStatus(status -> status.is4xxClientError(),
         response -> response.bodyToMono(String.class)
         .map(body -> new RuntimeException("curso no encontrado"))).bodyToMono(Map.class).block();   
            
    }

}
