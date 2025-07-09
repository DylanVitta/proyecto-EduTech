package com.example.evaluaciones.Controller;

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

import com.example.evaluaciones.controller.EvaluacionController;
import com.example.evaluaciones.model.Evaluacion;
import com.example.evaluaciones.service.EvaluacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EvaluacionController.class)
public class EvaluacionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvaluacionService evaluacionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void RegistrarNuevaEvaluacion() throws Exception {
        Evaluacion evaluacion = new Evaluacion(null, 1L, 2L, "PRUEBA", "Descripción", 6.0, null, null);
        Evaluacion guardada = new Evaluacion(1L, 1L, 2L, "PRUEBA", "Descripción", 6.0, LocalDate.now(), null);

        when(evaluacionService.saveEvaluacion(any(Evaluacion.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/v1/evaluaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evaluacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_evaluacion").value(1));
    }

    @Test
    void ListarTodasEvaluaciones() throws Exception {
        Evaluacion e = new Evaluacion(1L, 1L, 2L, "TAREA", "Desc", 5.5, LocalDate.now(), null);
        when(evaluacionService.getEvaluaciones()).thenReturn(List.of(e));

        mockMvc.perform(get("/api/v1/evaluaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
    @Test
    void ActualizarEvaluacionCasoSoloNota() throws Exception {
        Evaluacion evaluacion = new Evaluacion(1L, 1L, 2L, "PRUEBA", "Desc", 7.0, LocalDate.now(), null);

        when(evaluacionService.patchEvaluacion(1L, 7.0, null)).thenReturn(evaluacion);

        mockMvc.perform(patch("/api/v1/evaluaciones/1/modificar?nota=7.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nota").value(7.0));
    }

    @Test
    void ListarEvaluacionesPorAlumno_conSeleccionId() throws Exception {
        Evaluacion e = new Evaluacion(1L, 1L, 2L, "TAREA", "Desc", 6.0, LocalDate.now(), null);
        when(evaluacionService.getbyidalumno(2L)).thenReturn(List.of(e));

        mockMvc.perform(get("/api/v1/evaluaciones/alumno/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

     @Test
    void ObtenerEvaluacionEnEspecificoPorId() throws Exception {
        Evaluacion e = new Evaluacion(1L, 1L, 2L, "TAREA", "Desc", 5.0, LocalDate.now(), null);
        when(evaluacionService.getEvaluacion(1L)).thenReturn(e);

        mockMvc.perform(get("/api/v1/evaluaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_evaluacion").value(1));
    }

    @Test
    void EliminarEvaluacion() throws Exception {
        when(evaluacionService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/evaluaciones/1"))
                .andExpect(status().isNoContent());
    }

}
