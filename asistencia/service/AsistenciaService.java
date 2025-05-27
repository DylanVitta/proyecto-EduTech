package com.example.asistencia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.asistencia.model.Asistencia;
import com.example.asistencia.repository.AsistenciaRepository;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public Asistencia saveAsistencia(Asistencia asistencia){
        if(asistencia.getFecha()==null){
            asistencia.setFecha(LocalDate.now());

        }
        return asistenciaRepository.save(asistencia);
    }

    public List<Asistencia> getasistencias(){
        return asistenciaRepository.findAll();
    }
    public Asistencia getAsistencia(Long id){
        return asistenciaRepository.findById(id).orElseThrow
        (()-> new RuntimeException("Cliente no encontrado"));

    }
        public boolean deleteAsistencia( Long id){
       if( asistenciaRepository.existsById(id)){
        asistenciaRepository.deleteById(id);
        return true;
       }else{
        return false;
       }
    }

    

}
