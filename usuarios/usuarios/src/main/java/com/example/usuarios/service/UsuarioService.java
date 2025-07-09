package com.example.usuarios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usuarios.DTO.UsuarioDTO;
import com.example.usuarios.model.Login;
import com.example.usuarios.model.Rol;
import com.example.usuarios.model.usuario;
import com.example.usuarios.repository.RolRepository;
import com.example.usuarios.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> getUsuariosDTO() {
    List<usuario> usuarios = usuarioRepository.findAll();

    return usuarios.stream().map(usuario -> new UsuarioDTO(
        usuario.getId(),
        usuario.getNombres(),
        usuario.getApellidos(),
        usuario.getCorreo(),
        usuario.getRol()
    )).collect(Collectors.toList());}

    public UsuarioDTO login(Login login) {
        usuario user= usuarioRepository.findByCorreo(login.getCorreo()).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(login.getClave(), user.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return new UsuarioDTO(
            user.getId(),
            user.getNombres(),
            user.getApellidos(),
            user.getCorreo(),
            user.getRol());
    }


    public UsuarioDTO getUsuario(Long id){
        usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UsuarioDTO(
        usuario.getId(),
        usuario.getNombres(),
        usuario.getApellidos(),
        usuario.getCorreo(),
        usuario.getRol());
    }
    public usuario saveUsuario( usuario usuario){
        Rol rol = rolRepository.findById(usuario.getRol().getId_rol())
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
    }
    public usuario actualizarUsuario(Long id, usuario datos) {
        usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombres(datos.getNombres());
        usuario.setApellidos(datos.getApellidos());
        usuario.setCorreo(datos.getCorreo());
        
        if (datos.getRol() != null) {
            Rol rol = rolRepository.findById(datos.getRol().getId_rol())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(rol);
        }
        if (datos.getClave() != null && !datos.getClave().isEmpty()) {
            usuario.setClave(passwordEncoder.encode(datos.getClave())); 
        }
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
