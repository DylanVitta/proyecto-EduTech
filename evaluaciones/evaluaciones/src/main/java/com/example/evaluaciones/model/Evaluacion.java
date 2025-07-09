package com.example.evaluaciones.model;

import java.time.LocalDate;

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
@Table(name = "evaluaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "modelado de evaluaciones")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id unico de evaluacion")
    private Long id_evaluacion;

    @Column(nullable = false)
    @Schema(description = "id unico de curso")
    private Long idcurso;

    @Column
    @Schema(description = "id unico de alumno")
    private Long idalumnos;

    @Column(length = 15, nullable = false)
    @Schema(description = "tipo de evaluacion")
    private String tipo;

    @Column
    @Schema(description = "descripcion de la evaluacion")
    private String descripcion;
    @Column
    @Schema(description = "nota de la evaluacion")
    private double nota;

    @Column
    @Schema(description = "fecha en la que se subio la evaluacion")
    private LocalDate fechapubli;
    @Column
    @Schema(description = "fecha en la que se cierra la evaluacion")
    private LocalDate fechaLimite;

}
