package com.example.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuarios.DTO.UsuarioDTO;
import com.example.usuarios.model.usuario;
import com.example.usuarios.service.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService ;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerusuarios(){
        List<UsuarioDTO> usuarios = usuarioService.getusuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(usuarios);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerusuario(@PathVariable Long id){
        try{
            UsuarioDTO usuario = usuarioService.getUsuario(id);
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.notFound().build();

        }
    }
    @PostMapping
    public ResponseEntity<usuario> guardarusuario(@RequestBody usuario nuevo){
        return ResponseEntity.status(201).body(usuarioService.saveUsuario(nuevo));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarusuario (@PathVariable Long id ){
        if(usuarioService.deleteUsuario(id)){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
        
    }

    

}
