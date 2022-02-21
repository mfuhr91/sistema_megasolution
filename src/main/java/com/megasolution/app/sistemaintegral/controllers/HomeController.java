package com.megasolution.app.sistemaintegral.controllers;

import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.services.ClienteService;
import com.megasolution.app.sistemaintegral.services.SectorService;
import com.megasolution.app.sistemaintegral.services.ServicioService;
import com.megasolution.app.sistemaintegral.utils.Estado;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    private ClienteService clienteService;

    private ServicioService servicioService;

    private SectorService sectorService;

    public HomeController(ClienteService clienteService, ServicioService servicioService, SectorService sectorService) {
        this.clienteService = clienteService;
        this.servicioService = servicioService;
        this.sectorService = sectorService;
    }

    @GetMapping({"","/","/home","/index","/inicio"})
    public String inicio(Model model){
        Integer totalServicios = servicioService.contarServicios();
        Integer totalClientes = clienteService.contarClientes();
        Integer totalSectores = sectorService.contarTodos();
        List<Sector> sectoresDisponibles = sectorService.buscarDisponibles();
        Integer sectoresOcupados = totalSectores - sectoresDisponibles.size();
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
        model.addAttribute("totalSectores", totalSectores);
        model.addAttribute("sectoresOcupados", sectoresOcupados);
        model.addAttribute("serviciosDeHoy", serviciosDeHoy);
        model.addAttribute("tiempoPromedio", tiempo);
        model.addAttribute("totalPendientes", serviciosPendientes.size());
        model.addAttribute("totalEnProceso", serviciosEnProceso.size());
        model.addAttribute("totalTerminados", serviciosTerminados.size());
        model.addAttribute("totalEntregados", serviciosEntregados.size());
      
        return "inicio";
    }
}
