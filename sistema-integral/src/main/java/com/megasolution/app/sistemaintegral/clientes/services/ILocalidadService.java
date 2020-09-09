package com.megasolution.app.sistemaintegral.clientes.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Localidad;

public interface ILocalidadService {
    
    public List<Localidad> buscarTodos();

    public Localidad buscarPorId(Integer id);
}
