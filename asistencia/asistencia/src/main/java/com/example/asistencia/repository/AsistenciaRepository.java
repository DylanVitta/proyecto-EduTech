package com.example.asistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.asistencia.model.Asistencia;
@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia,Long> {

}
