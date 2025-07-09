package com.example.evaluaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.evaluaciones.WebClient.CursoClient;
import com.example.evaluaciones.WebClient.UsuarioClient;
import com.example.evaluaciones.model.Evaluacion;
import com.example.evaluaciones.repository.EvaluacionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private CursoClient cursoClient;
    @Autowired
    private UsuarioClient usuarioClient;

    public Evaluacion saveEvaluacion(Evaluacion evaluacion) {
        usuarioClient.obtenerusuarioId(evaluacion.getIdalumnos());
        cursoClient.obtenercursoId(evaluacion.getIdcurso());
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
    public List<Evaluacion> getbyidalumno(Long idalumno ){
        return evaluacionRepository.findByIdalumnos(idalumno);
    }
    public List<Evaluacion> getbyidcurso(Long idcurso){
        return evaluacionRepository.findByIdcurso(idcurso);
    }
    public boolean eliminar(Long id){
        if( evaluacionRepository.existsById(id)){
            evaluacionRepository.deleteById(id);
            return true;
        }else{
        return false;
    }
}
    public Evaluacion patchEvaluacion(Long id_evaluacion,Double nota,LocalDate fechaLimite ){
        Evaluacion evaluacion = evaluacionRepository.findById(id_evaluacion)
        .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));
        if (nota != null) {
        evaluacion.setNota(nota);
        }

        if (fechaLimite != null) {
        evaluacion.setFechaLimite(fechaLimite);
        }

        return evaluacionRepository.save(evaluacion);
        }


    }


