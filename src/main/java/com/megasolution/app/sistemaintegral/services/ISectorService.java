package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Sector;


public interface ISectorService {
    
    public List<Sector> buscarTodos();

    public List<Sector> buscarDisponibles();

    public Sector buscarPorId(Integer id);

    public void guardar(Sector sector);

    public void eliminar(Integer id);

    public Sector buscarPorNombre(String nombre);

    public List<Sector> buscarPorParametro(String param);
}
