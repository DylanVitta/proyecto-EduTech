package com.example.inscripcion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inscripcion.model.Inscripcion;
import com.example.inscripcion.service.InscripcionService;

@RestController
@RequestMapping("/api/v1/inscripcion")
public class InscripcionController {
    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<Inscripcion> inscribir(@RequestParam Long idusuario,@RequestParam Long idcurso){
        return ResponseEntity.ok(inscripcionService.saveInscripcion(idusuario, idcurso)); 
    }

    @GetMapping
    public ResponseEntity<List<Inscripcion>>  listarinscripciones (){
        List<Inscripcion> inscripcion = inscripcionService.getinscripciones();
        if(inscripcion.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(inscripcion);
    }        
    @GetMapping ("/{id}")
    public ResponseEntity<Inscripcion> obtenerusuario(@PathVariable Long id){
        try{
            Inscripcion inscripcion = inscripcionService.getisncripcion(id);
            return ResponseEntity.ok(inscripcion);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }    

    @GetMapping("/{idcurso}/cursos")
    public List<Inscripcion> obtenercursos(@PathVariable Long idcurso){
        return inscripcionService.getcurso(idcurso);
    }

    @PatchMapping("/{id}/estado") //--?activo=estado---/
    public ResponseEntity<Inscripcion> actualizarestado(@PathVariable Long id, @RequestParam boolean activo){
        return ResponseEntity.ok(inscripcionService.putEstado(id, activo));
    }




}