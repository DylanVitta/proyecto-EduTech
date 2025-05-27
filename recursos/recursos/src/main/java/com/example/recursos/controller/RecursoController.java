package com.example.recursos.controller;

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

import com.example.recursos.model.Recurso;
import com.example.recursos.service.RecursoService;

@RestController
@RequestMapping("/api/v1/recursos")
public class RecursoController {
    @Autowired
    private RecursoService recursoService;

    @GetMapping
    public ResponseEntity<List<Recurso>> obtenerRecursos(){
        List<Recurso> recursos= recursoService.getRecursos();
        if(recursos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recursos);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<Recurso> obtenerrecurso(@PathVariable Long id){
        try{
            Recurso recurso= recursoService.getrRecurso(id);
            return ResponseEntity.ok(recurso);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    } 

    @PostMapping
    public ResponseEntity<Recurso> crear(@RequestBody Recurso recurso){
        return ResponseEntity.status(201).body(recursoService.postRecurso(recurso));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idrecurso){
        recursoService.deleterecurso(idrecurso);
        return ResponseEntity.noContent().build();
    }

}
