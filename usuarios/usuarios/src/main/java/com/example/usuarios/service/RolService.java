package com.example.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuarios.model.Rol;
import com.example.usuarios.repository.RolRepository;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    public Rol getRol (Long id){
        return rolRepository.findById(id).orElseThrow(() -> new RuntimeException("ticket no encontrado"));
    }


    public Rol actualizarRol(Long id, Rol nuevoRol) {
        Rol rol = rolRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setNombre_rol(nuevoRol.getNombre_rol());
        return rolRepository.save(rol);
    }

    public Rol saveRol( Rol rol){
        return rolRepository.save(rol);
    }

    public List<Rol> getRoles(){
        return rolRepository.findAll();

    }

}
