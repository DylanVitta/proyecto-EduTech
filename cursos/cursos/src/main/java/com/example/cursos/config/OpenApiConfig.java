package com.example.cursos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
        .info(new io.swagger.v3.oas.models.info.Info()
            .title("cursos EduTech")
            .version("1.0")
            .description("microservicio encargado de todos los metodos de lso cursos de Edutech"));
    }

}
