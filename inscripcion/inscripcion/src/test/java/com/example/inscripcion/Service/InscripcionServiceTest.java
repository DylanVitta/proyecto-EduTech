package com.example.inscripcion.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.inscripcion.model.Inscripcion;
import com.example.inscripcion.repository.InscripcionRepository;
import com.example.inscripcion.service.InscripcionService;
import com.example.inscripcion.webClient.CursoClient;
import com.example.inscripcion.webClient.UsuarioCliente;

@ExtendWith(MockitoExtension.class)
public class InscripcionServiceTest {
    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private CursoClient cursoClient;

    @Mock
    private UsuarioCliente usuarioCliente;

    @InjectMocks
    private InscripcionService inscripcionService;
    @Test
    void testGuardarInscripcion_exito() {
        Long idCurso = 1L;
        Long idUsuario = 2L;

        when(inscripcionRepository.existsByIdcursoAndIdusuario(idCurso, idUsuario)).thenReturn(false);
        when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(i -> i.getArgument(0));

        when(cursoClient.obtenercursoId(idCurso)).thenReturn(new HashMap<>());
        when(usuarioCliente.obtenerusuarioId(idUsuario)).thenReturn(new HashMap<>());

    
        Inscripcion resultado = inscripcionService.saveInscripcion(idCurso, idUsuario);

        assertEquals(idCurso, resultado.getIdcurso());
        assertEquals(idUsuario, resultado.getIdusuario());
        assertTrue(resultado.isActivo());
        assertEquals(0.0, resultado.getProgreso());
        assertNotNull(resultado.getFecha());

        verify(inscripcionRepository).save(any(Inscripcion.class));
    }
    @Test
    void testGetUsuarios() {
        Long idUsuario = 2L;
        List<Inscripcion> lista = List.of(new Inscripcion(1L, LocalDate.now(), 0.0, true, idUsuario, 3L));

        when(inscripcionRepository.findByIdusuario(idUsuario)).thenReturn(lista);

        List<Inscripcion> resultado = inscripcionService.getusuarios(idUsuario);

        assertEquals(1, resultado.size());
        assertEquals(idUsuario, resultado.get(0).getIdusuario());
    }
    @Test
    void testGetTodasInscripciones() {
        List<Inscripcion> lista = List.of(
            new Inscripcion(1L, LocalDate.now(), 0.0, true, 1L, 1L),
            new Inscripcion(2L, LocalDate.now(), 0.5, false, 2L, 2L)
        );
        when(inscripcionRepository.findAll()).thenReturn(lista);

        List<Inscripcion> resultado = inscripcionService.getinscripciones();

        assertEquals(2, resultado.size());
    }
    @Test
    void testGetInscripcionPorId_existe() {
        Inscripcion insc = new Inscripcion(1L, LocalDate.now(), 0.0, true, 1L, 1L);
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(insc));

        Inscripcion resultado = inscripcionService.getisncripcion(1L);

        assertEquals(1L, resultado.getId());
    }
    @Test
    void testGetPorCurso() {
        Long idCurso = 3L;
        List<Inscripcion> lista = List.of(
            new Inscripcion(1L, LocalDate.now(), 0.0, true, 1L, idCurso)
        );
        when(inscripcionRepository.findByIdcurso(idCurso)).thenReturn(lista);

        List<Inscripcion> resultado = inscripcionService.getcurso(idCurso);

        assertEquals(1, resultado.size());
        assertEquals(idCurso, resultado.get(0).getIdcurso());
    }
    @Test
    void testActualizarEstado() {
        Inscripcion insc = new Inscripcion(1L, LocalDate.now(), 0.0, true, 1L, 1L);
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(insc));
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(insc);

        Inscripcion resultado = inscripcionService.putEstado(1L, false);

        assertFalse(resultado.isActivo());
        verify(inscripcionRepository).save(insc);
    }

}
