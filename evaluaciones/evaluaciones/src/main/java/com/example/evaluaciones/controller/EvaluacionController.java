package com.example.evaluaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.evaluaciones.model.Evaluacion;
import com.example.evaluaciones.service.EvaluacionService;

@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {
    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping
    public ResponseEntity<Evaluacion> registrar(@RequestBody Evaluacion evaluacion) {
        return ResponseEntity.ok(evaluacionService.saveEvaluacion(evaluacion));
    }    
    @GetMapping
    public List<Evaluacion> listar() {
        return evaluacionService.getEvaluaciones();
    }
    @GetMapping ("/{id}")
    public ResponseEntity<Evaluacion> obtenerevaluacionid(@PathVariable Long id){
        try{
            Evaluacion evaluacion = evaluacionService.getEvaluacion(id);
            return ResponseEntity.ok(evaluacion);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        evaluacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
