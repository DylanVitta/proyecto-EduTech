package com.example.soporte.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "soporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "modelado basico de microservicio de soporte, para dudas y consultas")
public class Soporte {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Schema(description = "id unico de ticket de soporte")
    private Long idconsulta;
    @Column(nullable = false)
    @Schema(description = "id unico del usuario que realizo la consulta")
    private Long usuarioid;
    @Column
    @Schema(description = "asunto mas global de lo que trata la consulta")
    private String asunto;
    @Column(nullable = false)
    @Schema(description = "consulta especifica a realizar")
    private String mensaje;
    
    @Column
    @Schema(description = "estado en el que se encuentra la consulta")
    private String estado;

    @Column(length = 20)
    @Schema(description = "fecha en la que se realizo la consulta")
    private LocalDateTime fecha;

}
