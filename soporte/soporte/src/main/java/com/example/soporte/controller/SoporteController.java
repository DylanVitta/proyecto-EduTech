package com.example.soporte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.soporte.model.Soporte;
import com.example.soporte.service.SoporteService;

@RestController
@RequestMapping("/api/v1/soporte")
public class SoporteController {
    @Autowired
    private SoporteService soporteService;

    @PostMapping
    public ResponseEntity<Soporte> crear (@RequestBody Soporte soporte){
        return ResponseEntity.ok(soporteService.saveticket(soporte));
    }

    @GetMapping
    public List<Soporte> listar(){
        return soporteService.getsoporte();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Soporte> obtenerticketid(@PathVariable Long id){
        try{
            Soporte ticket = soporteService.getTicket(id);
            return ResponseEntity.ok(ticket);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Soporte> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(soporteService.actualizarEstado(id, estado));
    }

}
