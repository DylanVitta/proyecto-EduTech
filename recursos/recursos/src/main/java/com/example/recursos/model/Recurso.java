package com.example.recursos.model;

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
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_recurso;

    @Column(nullable = false)
    private String titulo;
    @Column(nullable = true)
    private String descripcion;
    @Column(nullable = false)
    private String tipo;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private Long curso_id;

}
