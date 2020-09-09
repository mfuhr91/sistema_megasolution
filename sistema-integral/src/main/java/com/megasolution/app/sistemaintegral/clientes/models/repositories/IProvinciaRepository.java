package com.megasolution.app.sistemaintegral.clientes.models.repositories;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Provincia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProvinciaRepository extends JpaRepository<Provincia, Integer>{
    
}
