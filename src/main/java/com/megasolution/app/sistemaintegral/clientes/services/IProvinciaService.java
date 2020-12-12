package com.megasolution.app.sistemaintegral.clientes.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Provincia;

public interface IProvinciaService {
    
    public List<Provincia> buscarTodos();

    public Provincia buscarPorId(Integer id);
}
