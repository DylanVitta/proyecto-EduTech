package com.example.usuarios.Config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.usuarios.model.Rol;
import com.example.usuarios.model.usuario;
import com.example.usuarios.repository.RolRepository;
import com.example.usuarios.repository.UsuarioRepository;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner cargarDatos(RolRepository rolRepository, UsuarioRepository usuarioRepository,PasswordEncoder encoder) {
        return args -> {
            if (rolRepository.count() == 0) {
                Rol admin = new Rol(null, "ADMIN");
                Rol profesor = new Rol(null, "PROFESOR");
                Rol alumno = new Rol(null, "ALUMNO");

                rolRepository.saveAll(List.of(admin, profesor, alumno));

                
                if (usuarioRepository.count() == 0) {
                    usuario u1 = new usuario(null, "Carlos", "Pérez", "admin@correo.com", encoder.encode("1234"), admin);
                    usuario u2 = new usuario(null, "Laura", "Muñoz", "laura@correo.com", encoder.encode("1234"), profesor);
                    usuario u3 = new usuario(null, "Pedro", "Gómez", "pedro@correo.com", encoder.encode("1234"), alumno);
                    usuario u4 = new usuario(null, "Sofía", "Castro", "sofia@correo.com",encoder.encode ("1234"), alumno);
                    usuario u5 = new usuario(null, "Luis", "Fernández", "luis@correo.com", encoder.encode("1234"), profesor);

                    usuarioRepository.saveAll(List.of(u1, u2, u3, u4, u5));
                }
            }
        };
    }
}

