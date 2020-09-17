package com.megasolution.app.sistemaintegral.servicios.models.repositories;

import java.util.List;
import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer>{


    @Query(value = "SELECT * FROM servicios ORDER BY fecha_ingreso ASC;", nativeQuery = true)
    public List<Servicio> findAll();

    @Query(value = "SELECT * FROM servicios WHERE estado_id = ?1 ORDER BY fecha_ingreso ASC;", nativeQuery = true)
    public List<Servicio> findByEstadoServicio(Integer id);

    @Query(value = "SELECT COUNT(*) FROM servicios;", nativeQuery = true)
    public Integer contarServicios();

    @Query(value = "SELECT * FROM servicios WHERE cliente_id = ?1 ORDER BY fecha_ingreso ASC;", nativeQuery = true)
    public List<Servicio> findByServicioWithClienteId(Integer id);

    @Query(value = "SELECT * FROM servicios WHERE estado_id = ?1 AND cliente_id = ?2 ORDER BY fecha_ingreso ASC;", nativeQuery = true)
    public List<Servicio> findByServicioWithEstadoIdWithClienteId(Integer estado_id, Integer cliente_id);

    @Query(value = "SELECT * FROM servicios WHERE sector_id = ?1", nativeQuery = true)
    public Servicio buscarServicioPorSector(Integer id);

    @Query(value = "SELECT COUNT(*) FROM servicios WHERE fecha_ingreso LIKE ?1", nativeQuery = true)
    public Integer buscarServiciosDeHoy(String fechaHoy);


}
