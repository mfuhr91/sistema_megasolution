package com.megasolution.app.sistemaintegral.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.services.IClienteService;
import com.megasolution.app.sistemaintegral.services.IServicioService;

import com.megasolution.app.sistemaintegral.utils.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IServicioService servicioService;

    @GetMapping({"","/","/home","/index","/inicio"})
    public String inicio(Model model){
        Integer totalServicios = servicioService.contarServicios();
        Integer totalClientes = clienteService.contarClientes();
        long tiempo = servicioService.promedioServicios();
        List<Servicio> serviciosPendientes = servicioService.buscarPorEstadoServicioMonitor(Estado.PENDIENTE);
        List<Servicio> serviciosEnProceso = servicioService.buscarPorEstadoServicioMonitor(Estado.EN_PROCESO);
        List<Servicio> serviciosTerminados = servicioService.buscarPorEstadoServicioMonitor(Estado.TERMINADO);
        List<Servicio> serviciosEntregados = servicioService.buscarPorEstadoServicioMonitor(Estado.ENTREGADO);
        
        

        Date fechaHoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Integer serviciosDeHoy = servicioService.buscarServiciosDeHoy("%"+sdf.format(fechaHoy)+"%");
        
        model.addAttribute("titulo", "Inicio");
        model.addAttribute("active", "inicio");
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("totalServicios", totalServicios);
        model.addAttribute("serviciosDeHoy", serviciosDeHoy);
        model.addAttribute("tiempoPromedio", tiempo);
        model.addAttribute("totalPendientes", serviciosPendientes.size());
        model.addAttribute("totalEnProceso", serviciosEnProceso.size());
        model.addAttribute("totalTerminados", serviciosTerminados.size());
        model.addAttribute("totalEntregados", serviciosEntregados.size());
      
        return "inicio";
    }
}
