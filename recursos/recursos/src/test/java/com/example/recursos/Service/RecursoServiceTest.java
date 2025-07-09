package com.example.recursos.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.recursos.WebClient.CursoClient;
import com.example.recursos.model.Recurso;
import com.example.recursos.repository.RecursoRepository;
import com.example.recursos.service.RecursoService;

@ExtendWith(MockitoExtension.class)
public class RecursoServiceTest {
    @Mock
    private RecursoRepository recursoRepository;

    @Mock
    private CursoClient cursoClient;

    @InjectMocks
    private RecursoService recursoService;

    @Test
    void testGuardarRecurso_llamaCursoYGuarda() {
        Recurso recurso = new Recurso(null, "Título", "Descripción", "PDF", "http://recurso.pdf", 1L);
        Recurso guardado = new Recurso(10L, "Título", "Descripción", "PDF", "http://recurso.pdf", 1L);

        when(cursoClient.obtenerCursoPorId(1L)).thenReturn(new HashMap<>());
        when(recursoRepository.save(recurso)).thenReturn(guardado);

        Recurso resultado = recursoService.postRecurso(recurso);

        assertEquals("Título", resultado.getTitulo());
        verify(cursoClient).obtenerCursoPorId(1L);
        verify(recursoRepository).save(recurso);
    }
    @Test
    void testObtenerRecurso_existente_porId() {
        Recurso recurso = new Recurso(5L, "Video", null, "AUDIOVISUAL", "http://video.com", 2L);
        when(recursoRepository.findById(5L)).thenReturn(Optional.of(recurso));

        Recurso resultado = recursoService.getRecurso(5L);

        assertEquals("Video", resultado.getTitulo());
    }
    @Test
    void testObtenerPorCursoTodosLosRecursosDeUNcurso() {
        List<Recurso> lista = List.of(
            new Recurso(1L, "Guía 1", "Descripción", "Guía", "http://guia.pdf", 3L)
        );
        when(recursoRepository.findByCursoid(3L)).thenReturn(lista);

        List<Recurso> resultado = recursoService.obtenerPorCurso(3L);

        assertEquals(1, resultado.size());
        assertEquals("Guía 1", resultado.get(0).getTitulo());
    }
    @Test
    void ListarTodosLosRecursosTraeTodos() {
        List<Recurso> lista = List.of(
            new Recurso(1L, "Uno", null, "PDF", "url1", 1L),
            new Recurso(2L, "Dos", null, "Video", "url2", 2L)
        );
        when(recursoRepository.findAll()).thenReturn(lista);

        List<Recurso> resultado = recursoService.getRecursos();

        assertEquals(2, resultado.size());
    }
    @Test
    void ActualizarRecurso() {
        Recurso original = new Recurso(1L, "Antiguo", "desc", "PDF", "url", 5L);
        Recurso actualizado = new Recurso(null, "Nuevo", "desc nueva", "Video", "url2", 6L);

        when(recursoRepository.findById(1L)).thenReturn(Optional.of(original));
        when(cursoClient.obtenerCursoPorId(6L)).thenReturn(new HashMap<>());
        when(recursoRepository.save(any(Recurso.class))).thenReturn(original);

        Recurso resultado = recursoService.postRecurso(1L, actualizado);

        assertEquals("Nuevo", resultado.getTitulo());
        assertEquals("VIDEO", resultado.getTipo().toUpperCase());
        verify(cursoClient).obtenerCursoPorId(6L);
    }
    @Test
    void EliminarRecursoPorId() {
        recursoService.deleterecurso(1L);
        verify(recursoRepository).deleteById(1L);
    }

}
