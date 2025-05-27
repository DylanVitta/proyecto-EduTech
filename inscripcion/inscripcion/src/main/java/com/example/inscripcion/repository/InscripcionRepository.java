package com.example.inscripcion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inscripcion.model.Inscripcion;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion,Long> {
    List<Inscripcion> findByIdcurso(Long idcurso);
    List<Inscripcion> findByIdusuario(Long idusuario);
    boolean existsByIdcursoAndIdusuario(Long idcurso, Long idusuario);


}
