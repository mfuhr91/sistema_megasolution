package com.megasolution.app.sistemaintegral.clientes.models.repositories;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer>{


    @Query(value = "SELECT * FROM clientes ORDER BY razon_social ASC;", nativeQuery = true)
    public List<Cliente> findAll();

    public Cliente findByDniCuit(Long dniCuit);
    
    @Query(value = "SELECT COUNT(*) FROM clientes;", nativeQuery = true)
    public Integer contarClientes();
}
