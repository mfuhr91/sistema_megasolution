package com.megasolution.app.sistemaintegral.avisos.models.repositories;

import com.megasolution.app.sistemaintegral.avisos.models.entities.Mensaje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMensajeRepository extends JpaRepository<Mensaje, Integer>{
    
    @Query(value = "SELECT a.nombre FROM avisos a JOIN mensajes m ON a.mensaje_id = ?1;", nativeQuery = true)
    public String buscarAvisoDelMensaje(Integer id);

}
