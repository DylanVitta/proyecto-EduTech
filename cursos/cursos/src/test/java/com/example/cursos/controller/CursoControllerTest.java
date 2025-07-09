package com.example.cursos.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.cursos.model.Curso;
import com.example.cursos.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CursoController.class)
public class CursoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void crearCurso_retornaCreated() throws Exception {
        Curso curso = new Curso(null, "Java Básico", 2L, "Intro", "Programación", 20);
        Curso guardado = new Curso(1L, "Java Básico", 2L, "Intro", "Programación", 20);

        when(cursoService.saveCurso(any(Curso.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_curso").value(1));
    }

    @Test
    void listarCursos_retornaListacontodosloscursos() throws Exception {
        List<Curso> cursos = List.of(
            new Curso(1L, "Curso A", 2L, "Desc", "Cat", 10),
            new Curso(2L, "Curso B", 3L, "Desc", "Cat", 15)
        );

        when(cursoService.getCursos()).thenReturn(cursos);

        mockMvc.perform(get("/api/v1/cursos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void buscarPorId_retornaCurso() throws Exception {
        Curso curso = new Curso(1L, "Curso A", 2L, "Desc", "Cat", 10);
        when(cursoService.getCurso(1L)).thenReturn(curso);

        mockMvc.perform(get("/api/v1/cursos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre_curso").value("Curso A"));
    }

    @Test
    void filtrarPorProfesor_retornaCursos() throws Exception {
        List<Curso> cursos = List.of(
            new Curso(1L, "Java Avanzado", 2L, "Avanzado", "Backend", 30)
        );
        when(cursoService.getbyidprofesor(2L)).thenReturn(cursos);

        mockMvc.perform(get("/api/v1/cursos/profesor/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre_curso").value("Java Avanzado"));
    }
    @Test
    void eliminarCurso_existente_retornaNoContent() throws Exception {
        when(cursoService.deletecurso(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/v1/cursos/1"))
        .andExpect(status().isNoContent());
    }
    @Test
    void eliminarCurso_noExistente_retornaNotFound() throws Exception {
        when(cursoService.deletecurso(99L)).thenReturn(false);
        mockMvc.perform(delete("/api/v1/cursos/99"))
        .andExpect(status().isNotFound());
    }

}
