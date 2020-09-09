package com.megasolution.app.sistemaintegral.servicios.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.servicios.models.entities.Estado;

public interface IEstadoService {
    
    public List<Estado> buscarTodos();
    public Estado buscarPorId(Integer id);
}
