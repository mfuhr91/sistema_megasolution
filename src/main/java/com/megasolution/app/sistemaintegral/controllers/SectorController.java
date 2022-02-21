package com.megasolution.app.sistemaintegral.controllers;

import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.services.SectorService;
import com.megasolution.app.sistemaintegral.services.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@SessionAttributes("sector")
@RequestMapping("/sectores")
public class SectorController {

    private SectorService sectorService;

    private ServicioService servicioService;

    public SectorController(SectorService sectorService, ServicioService servicioService) {
        this.sectorService = sectorService;
        this.servicioService = servicioService;
    }

    @GetMapping("")
    public String listarSectores(Model model){

        List<Sector> sectores = sectorService.buscarTodos();
        
        model.addAttribute("titulo", "Sectores");
        model.addAttribute("sectores", sectores);
        model.addAttribute("active", "sectores");
        

        return "sectores/lista";    
    }

    @GetMapping("/editar/{id}")
    public String editarSector(@PathVariable Integer id, Model model, RedirectAttributes flash){
        Sector sector = null;
        if(sectorService.buscarPorId(id) == null){
            flash.addFlashAttribute("warning", "El sector no existe!");
            return "redirect:/sectores";
        }
        if(id>0){
            sector = sectorService.buscarPorId(id);
        }
        model.addAttribute("titulo", "Editar Sector");
        model.addAttribute("sector", sector);
        model.addAttribute("active", "sectores");


        return "sectores/form-sector";
    }
    @GetMapping("/nuevo")
    public String nuevoSector(Model model){
        Sector sector = new Sector();

        model.addAttribute("titulo", "Agregar sector");
        model.addAttribute("active", "sectores");
        model.addAttribute("sector", sector);

        return "sectores/form-sector";
    }

    @PostMapping("/guardar")
    public String guardarSector(@Valid Sector sector, BindingResult result, RedirectAttributes flash, Model model, SessionStatus status){
        Servicio servicio = servicioService.buscarServicioPorSector(sector.getId());
        Sector sectorBuscado = sectorService.buscarPorNombre(sector.getNombre());
        if(result.hasErrors()){
            model.addAttribute("titulo", "Agregar Sector");
            model.addAttribute("active", "sectores");
            return "sectores/form-sector";
        }
        if(sectorBuscado != null){
            flash.addFlashAttribute("warning", "El sector ya existe!");
            return "redirect:/sectores";
        }

        if(servicio != null){
            flash.addFlashAttribute("error", "El sector tiene un servicio asignado, liberelo para editarlo!");
            return "redirect:/sectores";
        }
        

        
        if(sector.getId() != null){
            sectorService.guardar(sector);
            status.setComplete();
            flash.addFlashAttribute("success", "Sector actualizado con éxito!");
        }else{
            sectorService.guardar(sector);
            status.setComplete();
            flash.addFlashAttribute("success", "Sector guardado con éxito!");
        }
        
        return "redirect:/sectores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSector(@PathVariable Integer id, RedirectAttributes flash){
        if(sectorService.buscarPorId(id) == null){
            flash.addFlashAttribute("error", "El sector no existe!");
            return "redirect:/sectores";
        }
        Sector sector = sectorService.buscarPorId(id);

        if(!sector.getDisponible()){

            flash.addFlashAttribute("error", "No puede eliminar un sector en uso!");
            return "redirect:/sectores";
        }
        
        sectorService.eliminar(id);
        flash.addFlashAttribute("success", "Sector eliminado con éxito!");
        return "redirect:/sectores";
    }

    @GetMapping("/buscar")
    public String buscarSector(@RequestParam String param, Model model, RedirectAttributes flash){
        
        if(param == ""){
            return "redirect:/sectores";
        }

        List<Sector> sectores = this.sectorService.buscarPorParametro(param);
        if(sectores.size() > 0){
        
            model.addAttribute("titulo", "Sectores");
            model.addAttribute("sectores", sectores);
            model.addAttribute("active", "sectores");
            
    
            return "sectores/lista";    
        } else {
            flash.addFlashAttribute("warning", "No se encontró ningún sector!");
            
            return "redirect:/sectores";
        }
    }  
    

}
