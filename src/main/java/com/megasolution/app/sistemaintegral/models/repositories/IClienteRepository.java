package com.megasolution.app.sistemaintegral.models.repositories;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer>{

    @Query(value = "SELECT * FROM clientes ORDER BY razon_social ASC;", nativeQuery = true)
    public List<Cliente> findAll();

    @Query(value = "SELECT * FROM clientes ORDER BY razon_social ASC LIMIT 100;", nativeQuery = true)
    public List<Cliente> find100();

    public Cliente findByDniCuit(Long dniCuit);
    
    @Query(value = "SELECT COUNT(*) FROM clientes;", nativeQuery = true)
    public Integer contarClientes();

    @Query(value =  "SELECT * FROM clientes AS c WHERE c.contacto LIKE %:param% " +
                    "OR c.direccion LIKE %:param% OR c.email LIKE %:param% " +
                    "OR c.razon_social LIKE %:param% OR c.telefono LIKE %:param% " +
                    "OR c.web LIKE %:param% OR c.dni_cuit LIKE %:param% ", nativeQuery = true)
    public List<Cliente> findByParam(@Param("param") String param);
}
