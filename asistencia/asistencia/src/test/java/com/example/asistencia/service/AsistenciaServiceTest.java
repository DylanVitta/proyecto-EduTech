package com.example.asistencia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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

import com.example.asistencia.WebClient.InscripcionClient;
import com.example.asistencia.model.Asistencia;
import com.example.asistencia.repository.AsistenciaRepository;

@ExtendWith(MockitoExtension.class)
public class AsistenciaServiceTest {
    @Mock
    private AsistenciaRepository asistenciaRepository;

    @Mock
    private InscripcionClient inscripcionClient;

    @InjectMocks
    private AsistenciaService asistenciaService;

    @Test
    void saveAsistencia_fechaNula_laSeteaYGuarda() {
        Asistencia nueva = new Asistencia(null, 1L, null, true);
        Asistencia guardada = new Asistencia(1L, 1L, LocalDate.now(), true);
        when(inscripcionClient.obtenerInscripcionPorId(1L)).thenReturn(new HashMap<>());
        when(asistenciaRepository.save(any())).thenReturn(guardada);
        Asistencia resultado = asistenciaService.saveAsistencia(nueva);
        assertNotNull(resultado.getFecha());
        assertEquals(true, resultado.isPresente());
    }

    @Test
    void getasistencias_devuelveLista() {
        List<Asistencia> lista = List.of(
            new Asistencia(1L, 1L, LocalDate.now(), true),
            new Asistencia(2L, 2L, LocalDate.now(), false)
        );

        when(asistenciaRepository.findAll()).thenReturn(lista);

        List<Asistencia> resultado = asistenciaService.getasistencias();
        assertEquals(2, resultado.size());
    }

    @Test
    void getAsistencia_existente_devuelveAsistencia() {
        Asistencia asistencia = new Asistencia(1L, 1L, LocalDate.now(), true);
        when(asistenciaRepository.findById(1L)).thenReturn(Optional.of(asistencia));

        Asistencia resultado = asistenciaService.getAsistencia(1L);

        assertEquals(1L, resultado.getId_asistencia());
    }
    @Test
    void patchPresente_actualizaCorrectamente() {
        Asistencia asistencia = new Asistencia(1L, 1L, LocalDate.now(), false);
        when(asistenciaRepository.findById(1L)).thenReturn(Optional.of(asistencia));
        when(asistenciaRepository.save(any())).thenReturn(asistencia);

        Asistencia resultado = asistenciaService.patchPresente(1L, true);

        assertTrue(resultado.isPresente());
    }
    @Test
    void deleteAsistencia_existente_eliminaYRetornaTrue() {
        when(asistenciaRepository.existsById(1L)).thenReturn(true);

        boolean resultado = asistenciaService.deleteAsistencia(1L);

        assertTrue(resultado);
        verify(asistenciaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteAsistencia_noExiste_retornaFalse() {
        when(asistenciaRepository.existsById(99L)).thenReturn(false);
        boolean resultado = asistenciaService.deleteAsistencia(99L);
        assertFalse(resultado);
        verify(asistenciaRepository, never()).deleteById(any());
    }
}
