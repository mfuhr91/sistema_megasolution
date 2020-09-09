package com.megasolution.app.sistemaintegral.clientes.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;

public interface IClienteService {

    public List<Cliente> buscarTodos();

    public Cliente buscarPorId(Integer id);

  /*   public Cliente buscarPorDniCuit(String dniCuit);

    public Cliente buscarPorRazonSocial(String razonSocial);

    public Cliente buscarPorContacto(String contacto);

    public Cliente buscarPorTelefono(Long telefono); */

    public void guardar(Cliente cliente);

    public void eliminar(Integer id);

    public Integer contarClientes();
    
}
