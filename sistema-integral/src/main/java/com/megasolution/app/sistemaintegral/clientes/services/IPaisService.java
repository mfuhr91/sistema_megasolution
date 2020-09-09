package com.megasolution.app.sistemaintegral.clientes.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Pais;

public interface IPaisService {
    
    public List<Pais> buscarTodos();

    public Pais buscarPorId(Integer id);
}
