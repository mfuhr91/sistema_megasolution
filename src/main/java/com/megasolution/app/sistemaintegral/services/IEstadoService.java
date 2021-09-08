package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Estado;

public interface IEstadoService {
    
    public List<Estado> buscarTodos();
    public Estado buscarPorId(Integer id);

    public Estado buscarPorCodigo(String codigo);
}
