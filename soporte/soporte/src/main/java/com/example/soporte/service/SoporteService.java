package com.example.soporte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soporte.WebClient.UsuarioClient;
import com.example.soporte.model.Soporte;
import com.example.soporte.repository.SoporteRepository;

@Service
public class SoporteService {
    @Autowired
    private SoporteRepository soporteRepository;
    @Autowired
    private UsuarioClient usuarioClient;

    public Soporte saveticket(Soporte soporte){
        usuarioClient.obtenerusuarioId(soporte.getUsuarioid());
        soporte.setEstado("Pendiente");
        soporte.setFecha(LocalDateTime.now());
        return soporteRepository.save(soporte);
    }
    public List<Soporte> getsoporte(){
        return soporteRepository.findAll();
    }
    public List<Soporte> getbyidusuario(Long id_usuario){
        return soporteRepository.findByUsuarioid(id_usuario);
    }
    public Soporte getTicket (Long id){
        return soporteRepository.findById(id).orElseThrow(
            () -> new RuntimeException("ticket no encontrado"));

    }
    public Soporte actualizarEstado(Long id, String estado){
        Soporte soporte = soporteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        soporte.setEstado(estado.toUpperCase());
        return soporteRepository.save(soporte);

    }

}
