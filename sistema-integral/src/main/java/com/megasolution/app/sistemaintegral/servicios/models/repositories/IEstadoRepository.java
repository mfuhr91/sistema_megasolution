package com.megasolution.app.sistemaintegral.servicios.models.repositories;

import com.megasolution.app.sistemaintegral.servicios.models.entities.Estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstadoRepository extends JpaRepository<Estado, Integer> {
    
}
