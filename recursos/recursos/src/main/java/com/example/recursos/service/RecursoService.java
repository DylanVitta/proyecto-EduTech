package com.example.recursos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.recursos.WebClient.CursoClient;
import com.example.recursos.model.Recurso;
import com.example.recursos.repository.RecursoRepository;

@Service
public class RecursoService {
    @Autowired
    private RecursoRepository recursoRepository;
    @Autowired
    private CursoClient cursoClient;

    public Recurso postRecurso(Recurso recurso){
        cursoClient.obtenerCursoPorId(recurso.getCursoid());
        return recursoRepository.save(recurso);
    }
    public Recurso getRecurso(Long id){
        return recursoRepository.findById(id).orElseThrow
        (()-> new RuntimeException("Recurso no encontrado"));

    }
    public List<Recurso> obtenerPorCurso(Long cursoId) {
        return recursoRepository.findByCursoid(cursoId);
    }
    public List<Recurso> getRecursos(){
        return recursoRepository.findAll();
    }
    public Recurso postRecurso(Long id, Recurso nuevo) {
        Recurso recurso = recursoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        recurso.setTitulo(nuevo.getTitulo());
        recurso.setDescripcion(nuevo.getDescripcion());
        recurso.setTipo(nuevo.getTipo());
        recurso.setUrl(nuevo.getUrl());
        if (!nuevo.getCursoid().equals(recurso.getCursoid())) {
            cursoClient.obtenerCursoPorId(nuevo.getCursoid()); 
            recurso.setCursoid(id);
            nuevo.getCursoid();
        }

        return recursoRepository.save(recurso);
    }

    public boolean deleterecurso(Long id) {
        try {
            recursoRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }


}
