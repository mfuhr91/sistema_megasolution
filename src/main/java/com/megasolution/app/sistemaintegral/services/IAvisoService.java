package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Aviso;

public interface IAvisoService {
    
    public List<Aviso> buscarTodos();

    public Aviso buscarPorId(Long id);

    public void guardar(Aviso aviso);

    public void eliminar(Long id);

    public List<Aviso> buscarAvisosNoLeidos();

    public Integer contarAvisos();

    public Aviso buscarAvisoPorServicioId(Integer id);

    public void cambiarANoLeido(); 

}
