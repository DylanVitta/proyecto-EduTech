package com.example.inscripcion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inscripcion.model.Inscripcion;
import com.example.inscripcion.repository.InscripcionRepository;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;


    public Inscripcion saveInscripcion(Long idcurso, Long idusuario){
        if (inscripcionRepository.existsByIdcursoAndIdusuario(idcurso, idusuario)) {
            throw new RuntimeException("El usuario ya está inscrito en este curso.");
        }
        Inscripcion inscripcion=new Inscripcion();
        inscripcion.setIdusuario(idusuario);
        inscripcion.setIdcurso(idcurso);
        inscripcion.setFecha(LocalDate.now());
        inscripcion.setProgreso(0.0);
        inscripcion.setActivo(true);

        return inscripcionRepository.save(inscripcion);
    }
    public List<Inscripcion> getusuarios(Long idusuario){
        return inscripcionRepository.findByIdusuario(idusuario);
    }
    public List<Inscripcion> getinscripciones(){
        return inscripcionRepository.findAll();
    }   
    public Inscripcion getisncripcion(Long id){
        return inscripcionRepository.findById(id).orElseThrow
        (()-> new RuntimeException("inscripcion no encontrada"));

    }
    public List<Inscripcion> getcurso( Long idcurso){
        return inscripcionRepository.findByIdcurso(idcurso);
    }
    public Inscripcion putEstado(Long id, boolean activo){
        Inscripcion inscripcion = inscripcionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada"));
        inscripcion.setActivo(activo);
        return inscripcionRepository.save(inscripcion);
    }

}
