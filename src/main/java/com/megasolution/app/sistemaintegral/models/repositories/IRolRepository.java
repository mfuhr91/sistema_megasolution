package com.megasolution.app.sistemaintegral.models.repositories;


import com.megasolution.app.sistemaintegral.models.entities.Rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer>{
    
}
