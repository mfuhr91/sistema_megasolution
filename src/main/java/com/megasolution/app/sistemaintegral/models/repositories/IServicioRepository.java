package com.megasolution.app.sistemaintegral.models.repositories;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;

import com.megasolution.app.sistemaintegral.utils.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer>{

    public List<Servicio> findByOrderByFechaIngresoDesc();

    @Query(value = "SELECT * FROM servicios ORDER BY fecha_ingreso DESC LIMIT 50", nativeQuery = true)
    public List<Servicio> findLast50();

    public List<Servicio> findByEstadoOrderByFechaIngresoAsc(Estado estado);

    public List<Servicio> findByEstadoOrderByFechaIngresoDesc(Estado estado);

    @Query(value = "SELECT * FROM servicios WHERE estado = ?1 ORDER BY observaciones LIKE '%URGENTE%' DESC, fecha_ingreso ASC", nativeQuery = true)
    public List<Servicio> findByEstadoServicioMonitor(String estado);

    public Integer countAllBy();

    @Query(value = "SELECT * FROM servicios WHERE cliente_id = ?1 ORDER BY fecha_ingreso ASC", nativeQuery = true)
    public List<Servicio> findByServicioWithClienteId(Integer id);

    public List<Servicio> findByEstadoAndClienteOrderByFechaIngresoAsc(Estado estado, Cliente cliente);

    public List<Servicio> findByEstadoAndClienteOrderByFechaIngresoDesc(Estado estado, Cliente cliente);

    @Query(value = "SELECT * FROM servicios WHERE sector_id = ?1", nativeQuery = true)
    public Servicio buscarServicioPorSector(Integer id);

    @Query(value = "SELECT COUNT(*) FROM servicios WHERE fecha_ingreso LIKE ?1", nativeQuery = true)
    public Integer buscarServiciosDeHoy(String fechaHoy);

    @Query(value = "SELECT * FROM servicios AS s INNER JOIN clientes  AS c ON s.cliente_id = c.id " +
                    "WHERE (s.equipo LIKE %:param% OR s.observaciones LIKE %:param% " + 
                    "OR s.problema_reportado LIKE %:param% OR s.solucion LIKE %:param% " +
                    "OR c.contacto LIKE %:param% OR c.razon_social LIKE %:param% " +
                    "OR c.direccion LIKE %:param% OR c.dni_cuit LIKE %:param% " +
                    "OR c.email LIKE %:param% OR c.telefono LIKE %:param%) AND s.estado LIKE %:estado% order by fecha_ingreso desc", nativeQuery = true)
    public List<Servicio> findByParam(@Param("param") String param,@Param("estado") String estado);
}
