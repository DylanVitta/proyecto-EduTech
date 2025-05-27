package com.example.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usuarios.model.usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<usuario,Long> {

}
