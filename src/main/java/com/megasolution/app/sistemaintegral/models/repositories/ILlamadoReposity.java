package com.megasolution.app.sistemaintegral.models.repositories;

import com.megasolution.app.sistemaintegral.models.entities.Llamado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILlamadoReposity extends JpaRepository<Llamado, Integer>{

    public Llamado findByNombreLlamado(String nombreLlamado);
    
}
