package com.example.inscripcion.model;

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
@Table(name = "inscripciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "modelo base de inscripcion")
public class Inscripcion {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Schema(description = "id unico de inscripcion")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "fecha de inscripcion")
    private LocalDate fecha;

    @Column(nullable = true)
    @Schema(description = "indicador de progreso realizado en el curso")
    private Double progreso;

    @Column(nullable = false)
    @Schema(description = "indicador de si la inscripcion esta activa o no")
    private boolean activo;

    @Column( name = "id_usuario" , nullable = false)
    @Schema(description = "id usuario al que pertenece la inscripcion")
    private Long idusuario;

    @Column(name = "id_curso",nullable=true)
    @Schema(description = "id unico de curso al que pertenece el usuario inscrito")
    private Long idcurso;



}
