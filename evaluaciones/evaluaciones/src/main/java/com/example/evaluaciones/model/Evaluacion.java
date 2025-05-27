package com.example.evaluaciones.model;

import java.time.LocalDate;

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
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_evaluacion;

    @Column(nullable = false)
    private Long idcurso;

    @Column
    private Long idalumnos;

    @Column(length = 15, nullable = false)
    private String tipo;

    @Column
    private String descripcion;
    @Column
    private double nota;

    @Column
    private LocalDate fechapubli;
    @Column
    private LocalDate fechaLimite;

}
