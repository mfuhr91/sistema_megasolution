package com.megasolution.app.sistemaintegral.servicios.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;



public interface IServicioService {


    public List<Servicio> buscarTodos();

    public Servicio buscarPorId(Integer id);

    public Integer contarServicios();

    public List<Servicio> buscarPorEstadoServicio(Integer id);
    
    public List<Servicio> buscarPorEstadoServicioMonitor(Integer id);

    public void guardar(Servicio servicio);

    public void eliminar(Integer id);

    public List<Servicio> buscarPorServicioConClienteId(Integer id);

    public List<Servicio> buscarPorEstadoPorCliente(Integer estadoId, Integer clienteId);

    public void recuperarEstadoTerminado(Servicio servicio);

    public Servicio buscarServicioPorSector(Integer id);

    public Integer buscarServiciosDeHoy(String fechaHoy);

    public long promedioServicios();

    public List<Servicio> buscarPorParametro(String param);
    
}
