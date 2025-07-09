package com.example.recursos.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.recursos.controller.RecursoController;
import com.example.recursos.model.Recurso;
import com.example.recursos.service.RecursoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RecursoController.class)
public class RecursoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecursoService recursoService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void testObtenerTodosLosRecursos_conContenido() throws Exception {
        List<Recurso> lista = List.of(new Recurso(1L, "Guía", "Desc", "PDF", "url", 1L));
        when(recursoService.getRecursos()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/recursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
    @Test
    void testObtenerPorCurso_conResultados() throws Exception {
        List<Recurso> lista = List.of(new Recurso(1L, "Guía", null, "PDF", "url", 5L));
        when(recursoService.obtenerPorCurso(5L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/recursos/curso/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
    @Test
    void testObtenerRecursoPorId_existe() throws Exception {
        Recurso recurso = new Recurso(1L, "Video", "Desc", "AUDIOVISUAL", "url", 1L);
        when(recursoService.getRecurso(1L)).thenReturn(recurso);

        mockMvc.perform(get("/api/v1/recursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Video"));
    }
    @Test
    void testCrearNuevoRecurso() throws Exception {
        Recurso recurso = new Recurso(null, "Guía", "Desc", "PDF", "url", 1L);
        Recurso guardado = new Recurso(10L, "Guía", "Desc", "PDF", "url", 1L);

        when(recursoService.postRecurso(any(Recurso.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/recursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recurso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_recurso").value(10L))
                .andExpect(jsonPath("$.tipo").value("PDF"));
    }
    @Test
    void testActualizarRecurso() throws Exception {
        Recurso actualizado = new Recurso(1L, "Nuevo", "Actualizado", "Video", "url2", 1L);

        when(recursoService.postRecurso(eq(1L), any(Recurso.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/recursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Nuevo"));
    }
    @Test
    void testEliminarRecurso_exito() throws Exception {
        when(recursoService.deleterecurso(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/recursos/1"))
        .andExpect(status().isNoContent());
    }
    

}
