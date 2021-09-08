package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Localidad;

public interface ILocalidadService {
    
    public List<Localidad> buscarTodos();

    public Localidad buscarPorId(Integer id);
}
