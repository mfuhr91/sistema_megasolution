package com.megasolution.app.sistemaintegral.models.repositories;

import com.megasolution.app.sistemaintegral.models.entities.Localidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocalidadRepository extends JpaRepository<Localidad, Integer>{
    
}
