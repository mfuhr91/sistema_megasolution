package com.megasolution.app.sistemaintegral.servicios.services;

import java.util.Date;
import java.util.List;

import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.servicios.models.repositories.IServicioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ServicioServiceImpl implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarTodos() {
        return servicioRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Servicio buscarPorId(Integer id) {
        return servicioRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer contarServicios() {
        return servicioRepo.contarServicios();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoServicio(Integer id) {
        return servicioRepo.findByEstadoServicio(id);
    }

    @Override
    @Transactional
    public void guardar(Servicio servicio) {
        servicioRepo.save(servicio);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        servicioRepo.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorServicioConClienteId(Integer id) {
      return servicioRepo.findByServicioWithClienteId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoPorCliente(Integer estadoId, Integer clienteId) {
        return servicioRepo.findByServicioWithEstadoIdWithClienteId(estadoId, clienteId);
    }

    public void recuperarEstadoTerminado(Servicio servicio){
        if(servicio.getEstado().getId() == 3){
            servicio.setFechaTerminado(new Date());
         }else{
             servicio.setFechaTerminado(null);
         }
    }

    @Override
    public Servicio buscarServicioPorSector(Integer id) {
        return servicioRepo.buscarServicioPorSector(id);
    }

    @Override
    public Integer buscarServiciosDeHoy(String fechaHoy) {
        return servicioRepo.buscarServiciosDeHoy(fechaHoy);
    }

    public long promedioServicios(){
        List<Servicio> servicios = servicioRepo.findByEstadoServicio(3);
        Date fechaActual = new Date();
        long tiempoTotal = 0;
        int nroServicios7Dias = 0;
        for (Servicio servicio : servicios) {
            
            if(fechaActual.getTime() - servicio.getFechaIngreso().getTime() <= 604800000){ // 7dias = 604800000 ms - 1dia = 86400000 - 1hr= 3600000
                nroServicios7Dias++;
                float tiempo = (servicio.getFechaTerminado().getTime() - servicio.getFechaIngreso().getTime());
                float tiempoEnHoras = (float) Math.ceil(tiempo / 3600000);
                tiempoTotal += tiempoEnHoras;
            }           
        }
        if(tiempoTotal != 0){
            return tiempoTotal / nroServicios7Dias;
        }else{
            return tiempoTotal;
        }

    }

    @Override
    public List<Servicio> buscarPorEstadoServicioMonitor(Integer id) {
        return servicioRepo.findByEstadoServicioMonitor(id);
    }

    @Override
    public List<Servicio> buscarPorParametro(String param) {

        param = param.toLowerCase();

        List<Servicio> servicios = this.servicioRepo.findByParam(param);
        return servicios;
    }

    @Override
    public List<Servicio> buscar50Ultimos() {
       return this.servicioRepo.findLast50();
    }
 
}
