package com.example.cursos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cursos.WebClient.UsuarioClient;
import com.example.cursos.model.Curso;
import com.example.cursos.repository.CursoRepository;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {
    @Mock
    private CursoRepository cursoRepo;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void crearCurso_devuelveCursoGuardado() {
        Curso nuevo = new Curso(null, "Java Básico", 2L, "Intro", "Programación", 20);
        Curso guardado = new Curso(1L, "Java Básico", 2L, "Intro", "Programación", 20);

        Map<String, Object> rol = new HashMap<>();
        rol.put("nombre", "PROFESOR");
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("rol", rol);

        when(usuarioClient.obtenerUsuarioPorId(2L)).thenReturn(usuario);

        when(cursoRepo.save(any(Curso.class))).thenReturn(guardado);

        Curso resultado = cursoService.saveCurso(nuevo);

        assertEquals("Java Básico", resultado.getNombre_curso());
        assertEquals(2L, resultado.getIdprofesor());
    }

    @Test
    void listarCursos_retornaLista() {
        List<Curso> lista = List.of(
            new Curso(1L, "Curso A", 2L, "desc", "cat", 10),
            new Curso(2L, "Curso B", 3L, "desc", "cat", 15)
        );

        when(cursoRepo.findAll()).thenReturn(lista);

        List<Curso> resultado = cursoService.getCursos();

        assertEquals(2, resultado.size());
    }

    @Test
    void getCurso_existente_retornaCurso() {
        Curso curso = new Curso(1L, "Curso A", 2L, "desc", "cat", 10);
        when(cursoRepo.findById(1L)).thenReturn(Optional.of(curso));

        Curso resultado = cursoService.getCurso(1L);

        assertEquals("Curso A", resultado.getNombre_curso());
    }

    @Test
    void filtrarPorProfesorid_retornaListaCursosdelProfesor() {
        List<Curso> cursos = List.of(new Curso(1L, "Java", 2L, "desc", "cat", 10));
        when(cursoRepo.findByIdprofesor(2L)).thenReturn(cursos);
        List<Curso> resultado = cursoService.getbyidprofesor(2L);
        assertEquals(1, resultado.size());
    }
    @Test
    void deletecurso_existente_retornaTrueYElimina() {
        Long id = 1L;
        when(cursoRepo.existsById(id)).thenReturn(true);
        boolean resultado = cursoService.deletecurso(id);
        assertTrue(resultado);
        verify(cursoRepo, times(1)).deleteById(id);
    }
}

