package com.megasolution.app.sistemaintegral.models.repositories;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Sector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISectorRepository extends JpaRepository<Sector, Integer>{
    
    @Query(value = "SELECT * FROM sectores AS s WHERE s.disabled = false ORDER BY nombre ASC;", nativeQuery = true)
    public List<Sector> findAll();

    @Query(value = "SELECT * FROM sectores s WHERE s.servicio_id is null AND s.disabled = false ORDER BY nombre ASC;", nativeQuery = true)
    public List<Sector> findByDisponible();

    public Sector findByNombre(String nombre);

    @Query(value = "SELECT * FROM sectores AS s WHERE s.disabled = false AND s.nombre LIKE %:param%", nativeQuery = true)
    public List<Sector> findByParam(@Param("param") String param);

    @Modifying
    @Query(value = "UPDATE sectores AS s SET s.disabled = true WHERE s.id = ?1", nativeQuery = true)
    public void disableSector(Integer id);

    public Integer countAllBy();
}
