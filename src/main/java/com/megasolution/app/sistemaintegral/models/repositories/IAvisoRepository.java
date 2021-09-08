package com.megasolution.app.sistemaintegral.models.repositories;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Aviso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAvisoRepository extends JpaRepository<Aviso, Long>{
    
    @Query(value = "SELECT COUNT(*) FROM avisos;", nativeQuery = true)
    public Integer contarAvisos();

    @Query(value = "SELECT * FROM avisos WHERE avisos.leido = false;", nativeQuery = true)
    public List<Aviso> buscarAvisosNoLeidos();
    
    @Query(value = "SELECT * FROM avisos WHERE avisos.servicio_id = ?1", nativeQuery = true)
    public Aviso buscarAvisoPorServicioId(Integer id);

    @Query(value = "SELECT * FROM avisos WHERE avisos.leido = true;", nativeQuery = true)
    public List<Aviso> buscarAvisosLeidos();


}
