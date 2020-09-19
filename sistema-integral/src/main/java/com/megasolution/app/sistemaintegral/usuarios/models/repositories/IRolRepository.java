package com.megasolution.app.sistemaintegral.usuarios.models.repositories;


import com.megasolution.app.sistemaintegral.usuarios.models.entities.Rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer>{
    
}
