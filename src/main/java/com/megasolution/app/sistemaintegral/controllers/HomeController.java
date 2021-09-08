package com.megasolution.app.sistemaintegral.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Aviso;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.services.IAvisoService;
import com.megasolution.app.sistemaintegral.services.IClienteService;
import com.megasolution.app.sistemaintegral.services.ILlamadoService;
import com.megasolution.app.sistemaintegral.services.IServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IAvisoService avisoService;

    @Autowired
    private IServicioService servicioService;

    @Autowired
    private ILlamadoService llamadoService;

    @GetMapping({"","/","/home","/index","/inicio"})
    public String inicio(Model model){
        List<Aviso> avisos = avisoService.buscarAvisosNoLeidos();
        Integer totalServicios = servicioService.contarServicios();
        Integer totalClientes = clienteService.contarClientes();
        Integer totalAvisos = avisos.size();
        long tiempo = servicioService.promedioServicios();
        List<Servicio> serviciosPendientes = servicioService.buscarPorEstadoServicioMonitor(1);
        List<Servicio> serviciosEnProceso = servicioService.buscarPorEstadoServicioMonitor(2);
        List<Servicio> serviciosTerminados = servicioService.buscarPorEstadoServicioMonitor(3);
        List<Servicio> serviciosEntregados = servicioService.buscarPorEstadoServicioMonitor(4);
        
        

        Date fechaHoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Integer serviciosDeHoy = servicioService.buscarServiciosDeHoy("%"+sdf.format(fechaHoy)+"%");
        
        model.addAttribute("titulo", "Inicio");
        model.addAttribute("active", "inicio");
        model.addAttribute("avisosNoLeidos", avisos);
        model.addAttribute("totalAvisos", totalAvisos);
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

    @GetMapping("/total-avisos")
    public @ResponseBody Integer totalAvisos(Model model){
        List<Aviso> avisos = avisoService.buscarAvisosNoLeidos();
        Integer totalAvisos = avisos.size();
        return totalAvisos;
    }

    @PostMapping("/aviso-leido")
    public @ResponseBody void avisoLeido(@RequestParam Long id, Model model){ 
        Aviso aviso = avisoService.buscarPorId(id);
        if(aviso.getLlamado().getId() == 1){
            aviso.setLlamado(llamadoService.buscarPorId(2));
        }else if(aviso.getLlamado().getId() == 2){
            aviso.setLlamado(llamadoService.buscarPorId(3));
        }else{
            aviso.setLlamado(null);
        }     
        aviso.setLeido(true);
        aviso.setFechaLeido(new Date());
    
        avisoService.guardar(aviso);
    }
}
