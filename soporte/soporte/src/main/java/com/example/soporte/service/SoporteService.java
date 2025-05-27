package com.example.soporte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soporte.model.Soporte;
import com.example.soporte.repository.SoporteRepository;

@Service
public class SoporteService {
    @Autowired
    private SoporteRepository soporteRepository;

    public Soporte saveticket(Soporte soporte){
        soporte.setEstado("Pendiente");
        soporte.setFecha(LocalDateTime.now());
        return soporteRepository.save(soporte);
    }
    public List<Soporte> getsoporte(){
        return soporteRepository.findAll();
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
