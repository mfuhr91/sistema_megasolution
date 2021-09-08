package com.megasolution.app.sistemaintegral.models.repositories;

import com.megasolution.app.sistemaintegral.models.entities.Provincia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProvinciaRepository extends JpaRepository<Provincia, Integer>{
    
}
