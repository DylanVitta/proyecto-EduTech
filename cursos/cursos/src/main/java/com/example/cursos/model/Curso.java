package com.example.cursos.model;

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
@Table(name = "cursos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "modelado de entidad curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id unico de curso")
    private Long id_curso;
    
    @Schema(description = "nombre de curso")
    @Column(nullable = false)
    private String nombre_curso;

    @Schema(description = "id unico de profesor del curso")
    @Column(nullable = false)
    private Long idprofesor;

    @Schema(description = "descripcion del curso")
    @Column(nullable = false)
    private String descripcion;

    @Schema(description = "categoria del curso")
    @Column(nullable = false)
    private String categoria;

    @Schema(description = "duracion estimada de horas del curso")
    @Column(nullable = false)
    private Integer duracion_horas;

}
