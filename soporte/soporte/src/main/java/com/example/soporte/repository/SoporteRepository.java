package com.example.soporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soporte.model.Soporte;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte,Long> {
    List<Soporte> findByUsuarioid(Long usuarioid);

}
