package com.example.recursos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recursos.model.Recurso;
import com.example.recursos.repository.RecursoRepository;

@Service
public class RecursoService {
    @Autowired
    private RecursoRepository recursoRepository;

    public Recurso postRecurso(Recurso recurso){
        return recursoRepository.save(recurso);
    }
    public Recurso getrRecurso(Long id){
        return recursoRepository.findById(id).orElseThrow
        (()-> new RuntimeException("Recurso no encontrado"));

    }
    public List<Recurso> getRecursos(){
        return recursoRepository.findAll();
    }

    public void deleterecurso(Long id){
        recursoRepository.deleteById(id);
    }


}
