package com.example.cursos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursos.model.Curso;
import com.example.cursos.repository.CursoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    
    public Curso saveCurso(Curso curso){
        return cursoRepository.save(curso);
    }

    public List<Curso> getCursos (){
        return cursoRepository.findAll();
    }

    public Curso getCurso (Long id){
        return cursoRepository.findById(id).orElseThrow
        (()-> new RuntimeException("Curso no encontrado"));
    }
    public Curso actualizaCurso( Long id , Curso nuevo ){
        Curso curso = cursoRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Curso no encontrado") );

        curso.setNombre_curso(nuevo.getNombre_curso());
        curso.setDescripcion(nuevo.getDescripcion());
        curso.setCategoria(nuevo.getCategoria());
        curso.setDuracion_horas(nuevo.getDuracion_horas());
        return cursoRepository.save(curso);
    }
    public boolean deletecurso(Long id){
        if( cursoRepository.existsById(id)){
        cursoRepository.deleteById(id);
        return true;
       }else{
        return false;
       }
            
    }
        
    
    

}
