package com.example.usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.usuarios.DTO.UsuarioDTO;
import com.example.usuarios.model.Login;
import com.example.usuarios.model.Rol;
import com.example.usuarios.model.usuario;
import com.example.usuarios.repository.RolRepository;
import com.example.usuarios.repository.UsuarioRepository;
import com.example.usuarios.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository repository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void login_valido_devuelveUsuarioDTO() {
        Rol rol = new Rol(1L, "ADMIN");
        usuario user = new usuario(1L, "Luis", "Pérez", "admin@correo.com", "encriptada123", rol);
        when(repository.findByCorreo("admin@correo.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encriptada123")).thenReturn(true);
        Login login = new Login("admin@correo.com", "1234");
        UsuarioDTO dto = usuarioService.login(login);
        assertEquals("Luis", dto.getNombres());
        assertEquals("admin@correo.com", dto.getCorreo());
    }
    @Test
    void login_conClaveIncorrecta_lanzaExcepcion() {
        usuario user = new usuario(1L, "Luis", "Pérez", "admin@correo.com", "encriptada123", new Rol(1L, "ADMIN"));
        when(repository.findByCorreo("admin@correo.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("mala", "encriptada123")).thenReturn(false);
        Login login = new Login("admin@correo.com", "mal");
        assertThrows(RuntimeException.class, () -> usuarioService.login(login));
    }
    @Test
    void guardarUsuario_exitoso() {
        Rol rol = new Rol(2L, "PROFESOR");
        usuario nuevo = new usuario(null, "Ana", "López", "ana@correo.com", "1234", rol);
        when(rolRepository.findById(2L)).thenReturn(Optional.of(rol));
        when(passwordEncoder.encode("1234")).thenReturn("encriptada123");
        when(repository.save(any(usuario.class))).thenAnswer(i -> {
            usuario u = i.getArgument(0);
            u.setId(10L);
            return u;
        });
        usuario guardado = usuarioService.saveUsuario(nuevo);
        assertNotNull(guardado.getId());
        assertEquals("encriptada123", guardado.getClave());
        assertEquals("Ana", guardado.getNombres());
    }
    @Test
    void getUsuariosDTO_devuelveListaDetodoslosusuairos() {
        Rol rol = new Rol(1L, "ADMIN");
        usuario user = new usuario(1L, "Carlos", "Pérez", "carlos@correo.com", "1234", rol);
        when(repository.findAll()).thenReturn(List.of(user));
        List<UsuarioDTO> dtos = usuarioService.getUsuariosDTO();
        assertEquals(1, dtos.size());
        assertEquals("Carlos", dtos.get(0).getNombres());
        assertEquals("Pérez", dtos.get(0).getApellidos());
    }
    @Test
    void getUsuario_existente_devuelveDTO() {
        Rol rol = new Rol(1L, "ADMIN");
        usuario user = new usuario(1L, "Luis", "Pérez", "luis@correo.com", "clave", rol);
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        UsuarioDTO dto = usuarioService.getUsuario(1L);
        assertEquals("Luis", dto.getNombres());
        assertEquals("luis@correo.com", dto.getCorreo());
    }
    @Test
    void actualizarUsuario_actualizaDatosCorrectamente() {
        Rol rolOriginal = new Rol(2L, "ALUMNO");
        Rol rolNuevo = new Rol(1L, "ADMIN");
        usuario existente = new usuario(1L, "Juan", "López", "juan@correo.com", "viejaClave", rolOriginal);
        usuario entrada = new usuario(null, "Juan", "López", "nuevo@correo.com", "nuevaClave", rolNuevo);
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rolNuevo));
        when(passwordEncoder.encode("nuevaClave")).thenReturn("encriptada");
        when(repository.save(any(usuario.class))).thenAnswer(i -> i.getArgument(0));
        usuario actualizado = usuarioService.actualizarUsuario(1L, entrada);
        assertEquals("nuevo@correo.com", actualizado.getCorreo());
        assertEquals("encriptada", actualizado.getClave());
        assertEquals("Juan", actualizado.getNombres());
    }
    @Test
    void deleteUsuario_existente_devuelveTrue() {
        when(repository.existsById(1L)).thenReturn(true);
        boolean resultado = usuarioService.deleteUsuario(1L);
        verify(repository).deleteById(1L);
        assertTrue(resultado);
    }
    @Test
    void deleteUsuario_noExistente_devuelveFalse() {
        when(repository.existsById(999L)).thenReturn(false);
        boolean resultado = usuarioService.deleteUsuario(999L);
        verify(repository, never()).deleteById(anyLong());
        assertFalse(resultado);
    }


    
}
