package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Llamado;

public interface ILlamadoService {

    public List<Llamado> buscarTodos();
    
    public Llamado buscarPorId(Integer id);

    public void actualizar(Llamado llamado);
}
