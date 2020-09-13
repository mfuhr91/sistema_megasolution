package com.megasolution.app.sistemaintegral.sectores.controllers;

import java.util.List;

import javax.validation.Valid;

import com.megasolution.app.sistemaintegral.sectores.models.entities.Sector;
import com.megasolution.app.sistemaintegral.sectores.services.ISectorService;
import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.servicios.services.IServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SessionAttributes("sector")
@RequestMapping("/sectores")
public class SectorController {
    
    @Autowired
    private ISectorService sectorService;

    @Autowired
    private IServicioService servicioService;

    @GetMapping("")
    public String listarSectores(Model model){

        List<Sector> sectores = sectorService.buscarTodos();
        
        model.addAttribute("titulo", "Sectores");
        model.addAttribute("sectores", sectores);
        model.addAttribute("active", "sectores");
        

        return "/sectores/lista";    
    }

    @GetMapping("/editar/{id}")
    public String editarSector(@PathVariable Integer id, Model model){
        Sector sector = null;

        if(id>0){
            sector = sectorService.buscarPorId(id);
        }
        model.addAttribute("titulo", "Editar Sector");
        model.addAttribute("sector", sector);
        model.addAttribute("active", "sectores");


        return "/sectores/form-sector";
    }
    @GetMapping("/nuevo")
    public String nuevoSector(Model model){
        Sector sector = new Sector();

        model.addAttribute("titulo", "Agregar sector");
        model.addAttribute("active", "sectores");
        model.addAttribute("sector", sector);

        return "/sectores/form-sector";
    }

    @PostMapping("/guardar")
    public String guardarSector(@Valid Sector sector, BindingResult result, RedirectAttributes flash, Model model, SessionStatus status){
        Servicio servicio = servicioService.buscarServicioPorSector(sector.getId());
        Sector sectorBuscado = sectorService.buscarPorNombre(sector.getNombre());
        if(result.hasErrors()){
            model.addAttribute("titulo", "Agregar Sector");
            model.addAttribute("active", "sectores");
            return "/sectores/form-sector";
        }
        if(sectorBuscado != null){
            return "redirect:/sectores";
        }

        if(servicio != null){
            return "redirect:/sectores";
        }
        

        sectorService.guardar(sector);
        status.setComplete();
        return "redirect:/sectores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSector(@PathVariable Integer id) {
        Sector sector = sectorService.buscarPorId(id);

        if(!sector.getDisponible()){


            return "redirect:/sectores";
        }
        
        sectorService.eliminar(id);
        return "redirect:/sectores";
    }
    

}
