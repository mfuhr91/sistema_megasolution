package com.megasolution.app.sistemaintegral.clientes.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;

public interface IClienteService {

    public List<Cliente> buscarTodos();

    public Cliente buscarPorId(Integer id);

    public void guardar(Cliente cliente);

    public void eliminar(Integer id);

    public Integer contarClientes();

    public Cliente buscarPorDniCuit(Long dniCuit);

    public List<Cliente> buscarPorParametro(String param);
    
}
