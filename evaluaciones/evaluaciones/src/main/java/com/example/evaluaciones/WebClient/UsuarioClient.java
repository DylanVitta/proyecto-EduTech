package com.example.evaluaciones.WebClient;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {
    private final WebClient webClient;

    public UsuarioClient(@Value("${usuario-service.url}") String clienteServidor){
        this.webClient = WebClient.builder().baseUrl(clienteServidor).build();
    }
    public Map<String, Object> obtenerusuarioId(Long id){
        
        return this.webClient.get()
        .uri("/{id}", id)
        .retrieve()
        .onStatus(status -> status.is4xxClientError(),
         response -> response.bodyToMono(String.class)
         .map(body -> new RuntimeException("usuario no encontrado"))).bodyToMono(Map.class).block();   
            
    }


}
