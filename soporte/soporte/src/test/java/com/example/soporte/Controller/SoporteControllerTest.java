package com.example.soporte.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.soporte.controller.SoporteController;
import com.example.soporte.model.Soporte;
import com.example.soporte.service.SoporteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SoporteController.class)
public class SoporteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoporteService soporteService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void CrearTicket_retornaStatusOkYmuestraTicket() throws Exception {
        Soporte soporte = new Soporte(null, 1L, "Asunto", "Mensaje", null, null);
        Soporte guardado = new Soporte(10L, 1L, "Asunto", "Mensaje", "Pendiente", LocalDateTime.now());

        when(soporteService.saveticket(any(Soporte.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/soporte")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(soporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idconsulta").value(10L))
                .andExpect(jsonPath("$.estado").value("Pendiente"));
    }
    @Test
    void devuelveunalistadeTodosLosTickets() throws Exception {
        List<Soporte> lista = List.of(new Soporte(1L, 1L, "Asunto", "Mensaje", "Pendiente", LocalDateTime.now()));
        when(soporteService.getsoporte()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/soporte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].estado").value("Pendiente"));
    }
    @Test
    void devuelveTicketsPorUsuario_listacorrespondiente() throws Exception {
        List<Soporte> lista = List.of(new Soporte(1L, 1L, "Asunto", "Mensaje", "Pendiente", LocalDateTime.now()));
        when(soporteService.getbyidusuario(1L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/soporte/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
     @Test
    void testObtenerTicketsPorUsuario_sinResultados() throws Exception {
        when(soporteService.getbyidusuario(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/soporte/usuario/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void ActualizarEstadodeTicket() throws Exception {
        Soporte soporteActualizado = new Soporte(1L, 1L, "Asunto", "Mensaje", "RESUELTO", LocalDateTime.now());
        when(soporteService.actualizarEstado(1L, "resuelto")).thenReturn(soporteActualizado);

        mockMvc.perform(patch("/api/v1/soporte/1/estado")
                .param("estado", "resuelto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("RESUELTO"));
    }
}
