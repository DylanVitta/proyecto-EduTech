package com.example.usuarios.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
        .info(new io.swagger.v3.oas.models.info.Info()
            .title("usuarios EduTech")
            .version("1.0")
            .description("administracion de usuarios de Edutech"));
    }

}
