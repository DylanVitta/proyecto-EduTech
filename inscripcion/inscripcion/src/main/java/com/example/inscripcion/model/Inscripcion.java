package com.example.inscripcion.model;

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
@Table(name = "inscripciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inscripcion {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = true)
    private Double progreso;

    @Column(nullable = false)
    private boolean activo;

    @Column( name = "id_usuario" , nullable = false)
    private Long idusuario;

    @Column(name = "id_curso",nullable=true)
    private Long idcurso;



}
