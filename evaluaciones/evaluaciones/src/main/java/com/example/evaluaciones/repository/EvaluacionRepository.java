package com.example.evaluaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.evaluaciones.model.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion,Long>{
    List<Evaluacion> findByIdalumnos(Long idalumnos); 

    List<Evaluacion> findByIdcurso(Long idcurso);     
}



