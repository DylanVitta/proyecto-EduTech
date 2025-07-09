package com.example.usuarios.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;

import com.example.usuarios.DTO.UsuarioDTO;
import com.example.usuarios.controller.UsuarioController;
import com.example.usuarios.model.Login;
import com.example.usuarios.model.Rol;
import com.example.usuarios.model.usuario;
import com.example.usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;
    @Test
    void listarUsuarios_devuelve200YJson() throws Exception {
        List<UsuarioDTO> usuarios = List.of(new UsuarioDTO(1L, "Carlos", "Pérez", "carlos@correo.com",new Rol(1L, "ADMIN")));
        when(usuarioService.getUsuariosDTO()).thenReturn(usuarios);
        mockMvc.perform(get("/api/v1/usuarios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].nombres").value("Carlos"));
    }
    private ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void actualizarUsuario_devuelve200YUsuarioActualizado() throws Exception {
        Long userId = 1L;

        Rol rol = new Rol(2L, "PROFESOR");

        usuario actualizado = new usuario(userId, "Carlos", "Pérez", "carlos@correo.com", "claveNueva", rol);

        when(usuarioService.actualizarUsuario(eq(userId), any(usuario.class)))
            .thenReturn(actualizado);

        usuario entrada = new usuario(null, "Carlos", "Pérez", "carlos@correo.com", "claveNueva", rol);

        mockMvc.perform(put("/api/v1/usuarios/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.correo").value("carlos@correo.com"));
    }
    @Test
    void loginUsuario_validoDevuelve200() throws Exception {
        Login login = new Login("admin@correo.com", "1234");
        UsuarioDTO dto = new UsuarioDTO(1L, "Carlos", "Pérez", "admin@correo.com", new Rol(1L, "ADMIN"));
        when(usuarioService.login(any(Login.class))).thenReturn(dto);
        
        mockMvc.perform(post("/api/v1/usuarios/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(login)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.correo").value("admin@correo.com"));}


    @Test
    void loginUsuario_invalidoDevuelve401() throws Exception {
        Login login = new Login("admin@correo.com", "claveIncorrecta");
        when(usuarioService.login(any(Login.class))).thenThrow(new RuntimeException("Credenciales inválidas"));
        mockMvc.perform(post("/api/v1/usuarios/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isUnauthorized());

    }
    @Test
    void guardarUsuario_devuelve201YUsuarioGuardado() throws Exception {
        Rol rol = new Rol(2L, "PROFESOR");
        usuario nuevo = new usuario(null, "Ana", "López", "ana@correo.com", "1234", rol);
        usuario guardado = new usuario(6L, "Ana", "López", "ana@correo.com", "1234", rol);
        when(usuarioService.saveUsuario(any(usuario.class))).thenReturn(guardado);
        mockMvc.perform(post("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevo)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(6L))
            .andExpect(jsonPath("$.correo").value("ana@correo.com"));
    }
    @Test
    void eliminarUsuario_existenteDevuelve204() throws Exception {
        Long id = 1L;
        when(usuarioService.deleteUsuario(id)).thenReturn(true);
        mockMvc.perform(delete("/api/v1/usuarios/{id}", id))
        .andExpect(status().isNoContent());
        verify(usuarioService).deleteUsuario(id);
    }
    @Test
    void eliminarUsuario_noExistenteDevuelve404() throws Exception {
        Long id = 999L;
        when(usuarioService.deleteUsuario(id)).thenReturn(false);
        mockMvc.perform(delete("/api/v1/usuarios/{id}", id))
        .andExpect(status().isNotFound());
        verify(usuarioService).deleteUsuario(id);
    }

}


