package com.megasolution.app.sistemaintegral.avisos.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.avisos.models.entities.Aviso;

public interface IAvisoService {
    
    public List<Aviso> buscarTodos();

    public Aviso buscarPorId(Integer id);

    public void guardar(Aviso aviso);

    public void eliminar(Integer id);

    public List<Aviso> buscarAvisosNoLeidos();

    public Integer contarAvisos();

    public Aviso buscarAvisoPorServicioId(Integer id);

    public void cambiarANoLeido(); 

}
