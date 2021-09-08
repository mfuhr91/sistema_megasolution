package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Cliente;

public interface IClienteService {

    public List<Cliente> buscarTodos();

    public List<Cliente> buscar100();

    public Cliente buscarPorId(Integer id);

    public void guardar(Cliente cliente);

    public void eliminar(Integer id);

    public Integer contarClientes();

    public Cliente buscarPorDniCuit(Long dniCuit);

    public List<Cliente> buscarPorParametro(String param);
    
}
