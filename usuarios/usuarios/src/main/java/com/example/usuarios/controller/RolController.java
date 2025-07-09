package com.example.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuarios.model.Rol;

import com.example.usuarios.service.RolService;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol){
        return ResponseEntity.status(201).body(rolService.saveRol(rol));
    }
    @GetMapping
    public ResponseEntity<List<Rol>> obtenerroles(){
        List<Rol>roles = rolService.getRoles();
        if(roles.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(roles);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Rol> modificarRol(@PathVariable Long id, @RequestBody Rol nuevo) {
        Rol actualizado = rolService.actualizarRol(id, nuevo);
        return ResponseEntity.ok(actualizado);
    }

}
