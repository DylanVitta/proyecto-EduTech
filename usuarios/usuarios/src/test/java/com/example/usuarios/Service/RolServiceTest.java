package com.example.usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.usuarios.model.Rol;

import com.example.usuarios.repository.RolRepository;

import com.example.usuarios.service.RolService;

@ExtendWith(MockitoExtension.class)
public class RolServiceTest {
    @Mock
    private RolRepository repository;

    @InjectMocks
    private RolService rolService;
    @Test
    void getRoles_devuelveListaDetodosloroles() {
        Rol rol = new Rol(1L, "ADMIN");
        when(repository.findAll()).thenReturn(List.of(rol));
        List<Rol> dtos = rolService.getRoles();
        assertEquals(1, dtos.size());
        assertEquals("ADMIN", dtos.get(0).getNombre_rol());
    }
    @Test
    void getRol_devuelverolenespecifico() {
        Rol rol = new Rol(1L, "ADMIN");
        when(repository.findById(1L)).thenReturn(Optional.of(rol));
        Rol dtos = rolService.getRol(1L);
        assertEquals("ADMIN", dtos.getNombre_rol());
    }

    @Test
    void guardarnuevoRol_exitoso() {
        Rol rol = new Rol(null, "PROFESOR");
        
        when(repository.save(any(Rol.class))).thenAnswer(i -> {
            Rol u = i.getArgument(0);
            u.setId_rol(10L);
            return u;
        });
        Rol nuevo = rolService.saveRol(rol);
        assertNotNull(nuevo.getId_rol());
        assertEquals("PROFESOR", nuevo.getNombre_rol());
    }
    @Test
    void actualizarnombredeusuairo_actualizaDatosCorrectamente() {
        Rol rolOriginal = new Rol(  1L, "ADMIN");
        Rol rolNuevo = new Rol(null, "ALUMNO");
        when(repository.findById(1L)).thenReturn(Optional.of(rolOriginal));
        when(repository.findById(1L)).thenReturn(Optional.of(rolNuevo));
        when(repository.save(any(Rol.class))).thenAnswer(i -> i.getArgument(0));
        Rol actualizado = rolService.actualizarRol(1L, rolNuevo);
        assertEquals("ALUMNO", actualizado.getNombre_rol());
    }
}
