package com.megasolution.app.sistemaintegral.avisos.controllers;

import java.util.List;

import javax.validation.Valid;

import com.megasolution.app.sistemaintegral.avisos.models.entities.Llamado;
import com.megasolution.app.sistemaintegral.avisos.services.ILlamadoService;

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
@RequestMapping("/llamados")
@SessionAttributes("llamado")
public class LlamadoController {

    @Autowired
    private ILlamadoService llamadoService;

    @GetMapping("")
    public String listarLlamados(Model model){
        List<Llamado> llamados = llamadoService.buscarTodos();
        
        model.addAttribute("llamados", llamados);
        model.addAttribute("titulo", "Llamados");
        model.addAttribute("active", "avisos");

        return "/llamados/lista";
    }
    @GetMapping("/editar/{id}")
    public String editarLlamado(@PathVariable Integer id, Model model){
        Llamado llamado = null;
        if(id > 1 ){
            llamado = llamadoService.buscarPorId(id);
        }else{
            model.addAttribute("llamado", llamado);
            model.addAttribute("active", "avisos");
            model.addAttribute("titulo", "Editar llamado");
            return "redirect:/llamados";
        }
        model.addAttribute("llamado", llamado);
        model.addAttribute("active", "avisos");
        model.addAttribute("titulo", "Editar llamado");

        return "/llamados/form-llamado";
    }
    @PostMapping("/actualizar")
    public String actualizarLlamado(@Valid Llamado llamado, BindingResult result, 
                            RedirectAttributes flash, Model model, SessionStatus status){
        if(llamado.getId() == 1){
            model.addAttribute("titulo", "Editar llamado");
            flash.addFlashAttribute("error", "El llamado no existe!");
            return "/llamados/form-llamado";
        }
        if(result.hasErrors()){
            model.addAttribute("titulo", "Editar llamado");
            return "/llamados/form-llamado";
        }
        llamadoService.actualizar(llamado);
        status.setComplete();
        flash.addFlashAttribute("success","Llamado actualizado con éxito!");
        return "redirect:/llamados";
    }
    
}
