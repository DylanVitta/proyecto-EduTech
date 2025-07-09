package com.example.evaluaciones.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.evaluaciones.WebClient.CursoClient;
import com.example.evaluaciones.WebClient.UsuarioClient;
import com.example.evaluaciones.model.Evaluacion;
import com.example.evaluaciones.repository.EvaluacionRepository;
import com.example.evaluaciones.service.EvaluacionService;

@ExtendWith(MockitoExtension.class)
public class EvaluacionesServiceTest {
    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private CursoClient cursoClient;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private EvaluacionService evaluacionService;

    @Test
    void GuardarEvaluacion_exito() {
        Evaluacion evaluacion = new Evaluacion(null, 1L, 2L, "Prueba", "Evaluación Final", 6.0, null, LocalDate.of(2025, 8, 1));
        Evaluacion guardada = new Evaluacion(10L, 1L, 2L, "Prueba", "Evaluación Final", 6.0, LocalDate.now(), LocalDate.of(2025, 8, 1));

        when(usuarioClient.obtenerusuarioId(2L)).thenReturn(new HashMap<>());
        when(cursoClient.obtenercursoId(1L)).thenReturn(new HashMap<>());
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(guardada);

        Evaluacion resultado = evaluacionService.saveEvaluacion(evaluacion);

        assertNotNull(resultado);
        assertEquals("Prueba", resultado.getTipo());
        verify(evaluacionRepository).save(any(Evaluacion.class));
    }

    @Test
    void GetEvaluaciones() {
        List<Evaluacion> lista = List.of(new Evaluacion(1L, 1L, 2L, "Tarea", "desc", 5.0, LocalDate.now(), LocalDate.now()));
        when(evaluacionRepository.findAll()).thenReturn(lista);

        List<Evaluacion> resultado = evaluacionService.getEvaluaciones();

        assertEquals(1, resultado.size());
    }
    @Test
    void GetEvaluacionPorId_existe() {
        Evaluacion evaluacion = new Evaluacion(1L, 1L, 2L, "Tarea", "desc", 6.5, LocalDate.now(), null);
        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacion));

        Evaluacion resultado = evaluacionService.getEvaluacion(1L);

        assertEquals(1L, resultado.getId_evaluacion());
    }
    @Test
    void GetEvaluacionesPorAlumno() {
        Long idAlumno = 2L;
        List<Evaluacion> lista = List.of(new Evaluacion(1L, 1L, idAlumno, "Tarea", "desc", 5.0, LocalDate.now(), null));
        when(evaluacionRepository.findByIdalumnos(idAlumno)).thenReturn(lista);

        List<Evaluacion> resultado = evaluacionService.getbyidalumno(idAlumno);

        assertEquals(1, resultado.size());
    }
    @Test
    void GetEvaluacionesPorCurso() {
        Long idCurso = 1L;
        List<Evaluacion> lista = List.of(new Evaluacion(1L, idCurso, 2L, "Tarea", "desc", 5.0, LocalDate.now(), null));
        when(evaluacionRepository.findByIdcurso(idCurso)).thenReturn(lista);

        List<Evaluacion> resultado = evaluacionService.getbyidcurso(idCurso);

        assertEquals(1, resultado.size());
    }

    @Test
    void testEliminarEvaluacion() {
        when(evaluacionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(evaluacionRepository).deleteById(1L);

        boolean resultado = evaluacionService.eliminar(1L);

        assertTrue(resultado);
        verify(evaluacionRepository).deleteById(1L);
    }

    @Test
    void PatchEvaluacion_actualizaNotaYfehcalmite() {
        Evaluacion original = new Evaluacion(1L, 1L, 2L, "Tarea", "desc", 4.5, LocalDate.now(), null);
        Evaluacion actualizada = new Evaluacion(1L, 1L, 2L, "Tarea", "desc", 6.0, LocalDate.now(), LocalDate.of(2025, 9, 1));

        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(original));
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(actualizada);

        Evaluacion resultado = evaluacionService.patchEvaluacion(1L, 6.0, LocalDate.of(2025, 9, 1));

        assertEquals(6.0, resultado.getNota());
        assertEquals(LocalDate.of(2025, 9, 1), resultado.getFechaLimite());
    }

}
