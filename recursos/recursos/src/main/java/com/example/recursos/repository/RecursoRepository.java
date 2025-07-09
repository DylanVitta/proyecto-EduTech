package com.example.recursos.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.recursos.model.Recurso;

@Repository
public interface RecursoRepository extends JpaRepository< Recurso, Long>{
    List<Recurso> findByCursoid(Long cursoid);
    
}
