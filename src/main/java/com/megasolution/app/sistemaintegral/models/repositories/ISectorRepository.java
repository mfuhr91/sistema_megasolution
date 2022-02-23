package com.megasolution.app.sistemaintegral.models.repositories;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Sector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISectorRepository extends JpaRepository<Sector, Integer>{
    
    @Query(value = "SELECT * FROM sectores ORDER BY nombre ASC;", nativeQuery = true)
    public List<Sector> findAll();

    @Query(value = "SELECT * FROM sectores s WHERE s.disponible = true ORDER BY nombre ASC;", nativeQuery = true)
    public List<Sector> findByDisponible();

    public Sector findByNombre(String nombre);

    @Query(value = "SELECT * FROM sectores AS s WHERE s.nombre LIKE %:param%", nativeQuery = true)
    public List<Sector> findByParam(@Param("param") String param);

    public Integer countAllBy();
}
