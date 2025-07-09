package com.example.usuarios.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import com.example.usuarios.controller.RolController;
import com.example.usuarios.model.Rol;
import com.example.usuarios.service.RolService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RolController.class)
public class RolControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void crearRol_devuelve201YObjeto() throws Exception {
        Rol nuevo = new Rol(null, "PROFESOR");
        Rol guardado = new Rol(1L, "PROFESOR");
        when(rolService.saveRol(any(Rol.class))).thenReturn(guardado);
        mockMvc.perform(post("/api/v1/roles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(nuevo)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id_rol").value(1L))
        .andExpect(jsonPath("$.nombre_rol").value("PROFESOR"));
    }
    @Test
    void obtenerRoles_devuelve200YLista() throws Exception {
        List<Rol> roles = List.of(new Rol(1L, "ADMIN"), new Rol(2L, "ALUMNO"));
        when(rolService.getRoles()).thenReturn(roles);
        mockMvc.perform(get("/api/v1/roles"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].nombre_rol").value("ADMIN"));
    }
    @Test
    void obtenerRoles_sinrolesYDevuelve204() throws Exception {
        when(rolService.getRoles()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/roles"))
        .andExpect(status().isNoContent());
    }
    @Test
    void modificarRol_devuelve200YActualizado() throws Exception {
        Rol actualizado = new Rol(1L, "ALUMNO");
        when(rolService.actualizarRol(eq(1L), any(Rol.class))).thenReturn(actualizado);
        mockMvc.perform(put("/api/v1/roles/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(actualizado)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nombre_rol").value("ALUMNO"));
}

}
