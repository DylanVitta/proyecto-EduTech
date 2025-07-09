package com.example.inscripcion.Controller;

import static org.mockito.Mockito.when;
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
import org.springframework.test.web.servlet.MockMvc;

import com.example.inscripcion.controller.InscripcionController;
import com.example.inscripcion.model.Inscripcion;
import com.example.inscripcion.service.InscripcionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(InscripcionController.class)
public class InscripcionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InscripcionService inscripcionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void InscribirUsuario_exitoGuardausuarionuevo() throws Exception {
        Inscripcion insc = new Inscripcion(1L, LocalDate.now(), 0.0, true, 2L, 3L);

        when(inscripcionService.saveInscripcion(2L, 3L)).thenReturn(insc);

        mockMvc.perform(post("/api/v1/inscripcion")
                .param("idusuario", "2")
                .param("idcurso", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
    @Test
    void ListarInscripciones_todassinfiltro() throws Exception {
        List<Inscripcion> lista = List.of(new Inscripcion(1L, LocalDate.now(), 0.0, true, 2L, 3L));
        when(inscripcionService.getinscripciones()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/inscripcion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
    @Test
    void ObtenerInscripcionPorId() throws Exception {
        Inscripcion insc = new Inscripcion(1L, LocalDate.now(), 0.0, true, 2L, 3L);
        when(inscripcionService.getisncripcion(1L)).thenReturn(insc);

        mockMvc.perform(get("/api/v1/inscripcion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
    @Test
    void ObtenerinscripcionesPorCurso() throws Exception {
        List<Inscripcion> lista = List.of(
            new Inscripcion(1L, LocalDate.now(), 0.0, true, 2L, 7L)
        );

        when(inscripcionService.getcurso(7L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/inscripcion/7/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
    @Test
    void ActualizarParametroEstado() throws Exception {
        Inscripcion actualizada = new Inscripcion(1L, LocalDate.now(), 0.0, false, 2L, 3L);

        when(inscripcionService.putEstado(1L, false)).thenReturn(actualizada);

        mockMvc.perform(patch("/api/v1/inscripcion/1/estado")
                .param("activo", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(false));
    }

}
