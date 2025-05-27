package com.example.usuarios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuarios.DTO.UsuarioDTO;
import com.example.usuarios.model.usuario;
import com.example.usuarios.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public List<UsuarioDTO> getusuarios(){
        return usuarioRepository.findAll().stream()
        .map(usuario -> new UsuarioDTO(
            usuario.getId(),
            usuario.getNombres(),
            usuario.getApellidos(),
            usuario.getCorreo(),
            usuario.getRol()
        ))
        .collect(Collectors.toList());
    }


    public UsuarioDTO getUsuario(Long id){
        usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UsuarioDTO(
        usuario.getId(),
        usuario.getNombres(),
        usuario.getApellidos(),
        usuario.getCorreo(),
        usuario.getRol()
    );

        

    }
    public usuario saveUsuario( usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public boolean deleteUsuario( Long id){
       if( usuarioRepository.existsById(id)){
        usuarioRepository.deleteById(id);
        return true;
       }else{
        return false;
       }
    }

    

}
