package com.example.soporte.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.soporte.WebClient.UsuarioClient;
import com.example.soporte.model.Soporte;
import com.example.soporte.repository.SoporteRepository;
import com.example.soporte.service.SoporteService;

@ExtendWith(MockitoExtension.class)
public class SoporteServiceTest {
     @Mock
    private SoporteRepository soporteRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private SoporteService soporteService;

    @Test
    void GuardarTicket_sinfechaniestado() {
        Soporte soporte = new Soporte(null,1L,"Problema de acceso","No puedo ingresar a la plataforma",null,null);

        Soporte soporteGuardado = new Soporte(1L,1L,"Problema de acceso","No puedo ingresar a la plataforma","Pendiente",LocalDateTime.now());

        when(usuarioClient.obtenerusuarioId(1L)).thenReturn(new HashMap<>());
        when(soporteRepository.save(any(Soporte.class))).thenReturn(soporteGuardado);

        Soporte resultado = soporteService.saveticket(soporte);

        assertEquals("Pendiente", resultado.getEstado());
        assertNotNull(resultado.getFecha());
        verify(usuarioClient).obtenerusuarioId(1L);
        verify(soporteRepository).save(any(Soporte.class));
    }
    @Test
    void ObtenerTodosLosSoportes() {
        Soporte soporte = new Soporte(1L,1L,"Asunto","Mensaje","Pendiente",LocalDateTime.now());

        when(soporteRepository.findAll()).thenReturn(List.of(soporte));

        List<Soporte> resultado = soporteService.getsoporte();

        assertEquals(1, resultado.size());
        assertEquals("Asunto", resultado.get(0).getAsunto());
    }
    @Test
    void ObtenerPorIdUsuario() {
        Soporte soporte = new Soporte(2L,1L,"Login","No puedo entrar","Pendiente",LocalDateTime.now());

        when(soporteRepository.findByUsuarioid(1L)).thenReturn(List.of(soporte));

        List<Soporte> resultado = soporteService.getbyidusuario(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getUsuarioid());
    }
    @Test
    void testObtenerTicketPorIdTicket() {
        Soporte soporte = new Soporte(10L,3L,"Consulta general","¿Cuándo es el examen?","Pendiente",LocalDateTime.now());

        when(soporteRepository.findById(10L)).thenReturn(Optional.of(soporte));

        Soporte resultado = soporteService.getTicket(10L);

        assertEquals("Consulta general", resultado.getAsunto());
    }
    @Test
    void testActualizarEstadoTicket() {
        Soporte soporte = new Soporte(5L,2L,"Cambio de clave","Olvidé mi contraseña","Pendiente",LocalDateTime.now());

        when(soporteRepository.findById(5L)).thenReturn(Optional.of(soporte));
        when(soporteRepository.save(any(Soporte.class))).thenReturn(soporte);

        Soporte resultado = soporteService.actualizarEstado(5L, "resuelto");

        assertEquals("RESUELTO", resultado.getEstado());
        verify(soporteRepository).save(any(Soporte.class));
    }


}
