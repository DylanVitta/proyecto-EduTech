package com.example.evaluaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.evaluaciones.model.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion,Long>{

}
