package com.megasolution.app.sistemaintegral.clientes.models.repositories;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Pais;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaisRepository extends JpaRepository<Pais, Integer>{
    
}
