package com.example.soporte.model;

import java.time.LocalDateTime;

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
public class Soporte {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long idconsulta;
    @Column(nullable = false)
    private Long usuarioid;
    @Column
    private String asunto;
    @Column(nullable = false)
    private String mensaje;
    
    @Column
    private String estado;

    @Column
    private LocalDateTime fecha;

}
