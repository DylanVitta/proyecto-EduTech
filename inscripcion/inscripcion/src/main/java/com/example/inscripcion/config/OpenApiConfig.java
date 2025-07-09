package com.example.inscripcion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
        .info(new io.swagger.v3.oas.models.info.Info()
            .title("Registro inscripciones EduTech")
            .version("1.0")
            .description("Administracion de inscripciones de Edutech"));
    }

}
