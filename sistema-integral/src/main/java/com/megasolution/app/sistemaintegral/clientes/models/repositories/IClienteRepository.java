package com.megasolution.app.sistemaintegral.clientes.models.repositories;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer>{


   /*  @Query("select c from Cliente c where c.dni_cuit like %?1%")
    public Cliente findByDniCuit(String dniCuit);

    @Query("select c from Cliente c where c.razonSocial like %?1%")
    public Cliente findByRazonSocial(String razonSocial);

    @Query("select c from Cliente c where c.contacto like %?1%")
    public Cliente findByContacto(String contacto);

    @Query("select c from Cliente c where c.telefono like %?1%")
    public Cliente findByTelefono(Long telefono); */

    @Query(value = "SELECT COUNT(*) FROM clientes;", nativeQuery = true)
    public Integer contarClientes();
}
