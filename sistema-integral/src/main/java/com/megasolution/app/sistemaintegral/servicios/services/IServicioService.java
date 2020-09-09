package com.megasolution.app.sistemaintegral.servicios.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;



public interface IServicioService {


    public List<Servicio> buscarTodos();

    public Servicio buscarPorId(Integer id);
    
    public Servicio buscarPorCliente(Cliente cliente);

    public Integer contarServicios();

    public List<Servicio> buscarPorEstadoServicio(Integer id);

    public Servicio guardar(Servicio servicio);

    public void eliminar(Integer id);

    public List<Servicio> buscarPorServicioConClienteId(Integer id);

    public List<Servicio> buscarPorEstadoPorCliente(Integer estado_id, Integer cliente_id);

    public void recuperarEstadoTerminado(Servicio servicio);
    
}
