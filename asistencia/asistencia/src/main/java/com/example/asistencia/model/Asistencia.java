package com.example.asistencia.model;

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
@Table(name = "Asistencia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="Modelo para registrar asistencia")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "identificador unico para cada regsitro de asistencia")
    private Long id_asistencia;
    @Schema(description = "Inscripcion a la cual esta unida cada asisetncia")
    @Column(nullable = false)
    private Long inscripcionid;
    @Schema(description = "fecha en la cual se registro la asistencia")
    @Column(nullable = true)
    private LocalDate fecha;
    @Schema(description = "indicacion de la asistencia, presente/ausente(verdadero o falso)")
    @Column(nullable = false)
    private boolean presente;



}
