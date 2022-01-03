package com.megasolution.app.sistemaintegral.services;

import java.io.IOException;
import java.util.List;

import com.megasolution.app.sistemaintegral.models.ServicioModel;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.utils.Estado;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;

import com.megasolution.app.sistemaintegral.utils.TipoMail;
import org.springframework.ui.Model;



public interface IServicioService {


    public List<Servicio> buscarTodos();

    public List<Servicio> buscar50Ultimos();

    public Servicio buscarPorId(Integer id);

    public Integer contarServicios();

    public List<Servicio> buscarPorEstadoServicio(Estado estado);
    
    public List<Servicio> buscarPorEstadoServicioMonitor(Estado estado);

    public void guardar(Servicio servicio);

    public void eliminar(Integer id);

    public List<Servicio> buscarPorServicioConClienteId(Integer id);

    public List<Servicio> buscarPorEstadoPorCliente(Estado estado, Cliente cliente);

    public void recuperarEstadoTerminado(Servicio servicio);

    void almacenarSectorAnterior(Sector sector);

    public Servicio buscarServicioPorSector(Integer id);

    public Integer buscarServiciosDeHoy(String fechaHoy);

    public long promedioServicios();

    public List<Servicio> buscarPorParametro(String param, String estado);

    public Model validarForm(Servicio servicio, Model model);

    public void enviarMail(Servicio servicio);

    public Model enviarModelo(ServicioModel servicioModel, Model model);

    public List<Servicio> buscarPorParamEstado(String param, String estado);

    public void asignarSector(Servicio servicio, Sector sector);

    public Model listarSegunClienteEstado(Integer id, Estado estado, Model model);

    public Model listarSegunEstado(Cliente cliente, Estado estado, Model model);
}
