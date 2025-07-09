package com.example.usuarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "modelado base del usuario participe de edutech")
public class usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "identificador unico del usuario")
    private Long id;

    @Column(length = 100, nullable = false)
    @Schema(description = "nombres del usuario")
    private String nombres;

    @Column(nullable = false)
    @Schema(description = "apellidos del usuario")
    private String apellidos;

    @Column(nullable = false)
    @Schema(description = " correo electronico del usuario")
    private String correo;

    @Column(nullable = false)
    @Schema(description = "clave de la cuenta del usuario")
    private String clave;

    @ManyToOne
    @JoinColumn(name = "rol")
    @Schema(description = "rol al que pertenece el usuario")
    private Rol rol;

}
