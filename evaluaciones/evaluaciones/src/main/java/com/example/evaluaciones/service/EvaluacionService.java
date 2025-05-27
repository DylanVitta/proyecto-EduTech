package com.example.evaluaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.evaluaciones.model.Evaluacion;
import com.example.evaluaciones.repository.EvaluacionRepository;

@Service
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public Evaluacion saveEvaluacion(Evaluacion evaluacion) {
        if (evaluacion.getFechapubli()== null) {
            evaluacion.setFechapubli(LocalDate.now());
        }
        return evaluacionRepository.save(evaluacion);
    }
    public List<Evaluacion> getEvaluaciones() {
        return evaluacionRepository.findAll();
    }
    public Evaluacion getEvaluacion(Long id){
        return evaluacionRepository.findById(id).orElseThrow
        (()-> new RuntimeException("Cliente no encontrado"));

    }
    public void eliminar(Long id) {
        evaluacionRepository.deleteById(id);
    }
}
