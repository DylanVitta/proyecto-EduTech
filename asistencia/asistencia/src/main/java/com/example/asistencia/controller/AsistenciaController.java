package com.example.asistencia.controller;

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

import com.example.asistencia.model.Asistencia;
import com.example.asistencia.service.AsistenciaService;

@RestController
@RequestMapping("/api/v1/asistencia")
public class AsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping
    public ResponseEntity<Asistencia> registrar(@RequestBody Asistencia asistencia) {
        return ResponseEntity.ok(asistenciaService.saveAsistencia(asistencia));
    }

    @GetMapping
    public List<Asistencia> listar() {
        return asistenciaService.getasistencias();
    }    
    @GetMapping ("/{id}")
    public ResponseEntity<Asistencia> obtenerasistenciaId(@PathVariable Long id){
        try{
            Asistencia asistencia = asistenciaService.getAsistencia(id);
            return ResponseEntity.ok(asistencia);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarasistencia (@PathVariable Long id ){
        if(asistenciaService.deleteAsistencia(id)){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
