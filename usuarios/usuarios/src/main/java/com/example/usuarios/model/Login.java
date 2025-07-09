package com.example.usuarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Inicio de sesion del sistema")
public class Login {
    @Schema(description = "correo del usuario que quiere iniciar sesion")
    private String correo;
    @Schema(description = "clave del usuario que quiere inicar sesion")
    private String clave;

}
