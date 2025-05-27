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

    public Rol saveRol( Rol rol){
        return rolRepository.save(rol);
    }

    public List<Rol> getRoles(){
        return rolRepository.findAll();

    }

}
