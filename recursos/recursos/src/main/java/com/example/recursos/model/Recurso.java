package com.example.recursos.model;

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
@Table(name = "Recursos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description ="Modelado base de recursos" )
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id unico de recurso")
    private Long id_recurso;

    @Column(nullable = false)
    @Schema(description = "titulo de recurso")
    private String titulo;
    @Column(nullable = true)
    @Schema(description = "descripcion de recurso")
    private String descripcion;
    @Column(nullable = false)
    @Schema(description = "tipo de recurso esperado, aduiovisual,guia, etc.")
    private String tipo;
    @Column(nullable = false)
    @Schema(description = "url, link de descarga o enlace directo del recurso")
    private String url;
    @Column(nullable = false)
    @Schema(description = "id unico del curso al cul va dirigido el recurso")
    private Long cursoid;

}
