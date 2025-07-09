package com.example.cursos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cursos.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {
    List<Curso> findByIdprofesor(Long idprofesor);

}
