package com.example.cursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursos.model.Curso;
import com.example.cursos.service.CursoService;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<Curso> nuevocurso(@RequestBody Curso nuevo){
        return ResponseEntity.status(201).body(cursoService.saveCurso(nuevo));
    }
    @GetMapping
    public ResponseEntity<List<Curso>> obtenercursos(){
        List<Curso> cursos = cursoService.getCursos();
        if(cursos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(cursos);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<Curso> obtenercurso(@PathVariable Long id){
        try{
            Curso curso = cursoService.getCurso(id);
            return ResponseEntity.ok(curso);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizarcurso(@PathVariable Long id,@ RequestBody Curso curso){
        try{
            return ResponseEntity.ok(cursoService.actualizaCurso(id, curso));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarcurso (@PathVariable Long id ){
        if(cursoService.deletecurso(id)){
            
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
        
    }


}
