package com.example.asistencia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.asistencia.model.Asistencia;
import com.example.asistencia.service.AsistenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AsistenciaController.class)
public class AsistenciaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsistenciaService asistenciaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrar_retornaAsistenciaGuardada() throws Exception {
        Asistencia nueva = new Asistencia(null, 1L, null, true);
        Asistencia guardada = new Asistencia(1L, 1L, LocalDate.now(), true);

        when(asistenciaService.saveAsistencia(any())).thenReturn(guardada);

        mockMvc.perform(post("/api/v1/asistencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nueva)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.inscripcionid").value(1));
    }

    @Test
    void listar_retornaListaAsistencias() throws Exception {
        List<Asistencia> lista = List.of(
            new Asistencia(1L, 1L, LocalDate.now(), true),
            new Asistencia(2L, 1L, LocalDate.now(), false)
        );

        when(asistenciaService.getasistencias()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/asistencia"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void buscarPorId_devuelveAsistencia() throws Exception {
        Asistencia asistencia = new Asistencia(1L, 1L, LocalDate.now(), true);
        when(asistenciaService.getAsistencia(1L)).thenReturn(asistencia);

        mockMvc.perform(get("/api/v1/asistencia/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.presente").value(true));
    }

    @Test
    void actualizarPresente_retornaActualizado() throws Exception {
        Asistencia asistencia = new Asistencia(1L, 1L, LocalDate.now(), true);
        when(asistenciaService.patchPresente(1L, false)).thenReturn(asistencia);

        mockMvc.perform(patch("/api/v1/asistencia/1/presente?presente=false"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.presente").value(true));
    }

    @Test
    void eliminar_existente_retornaNoContent() throws Exception {
        when(asistenciaService.deleteAsistencia(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/asistencia/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_noExistente_retornaNotFound() throws Exception {
        when(asistenciaService.deleteAsistencia(100L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/asistencia/100"))
            .andExpect(status().isNotFound());
    }

}
