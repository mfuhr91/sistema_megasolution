package com.megasolution.app.sistemaintegral.avisos.controllers;

import java.util.List;

import javax.validation.Valid;


import com.megasolution.app.sistemaintegral.avisos.models.entities.Mensaje;
import com.megasolution.app.sistemaintegral.avisos.services.IMensajeService;


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
@SessionAttributes("mensaje")
@RequestMapping("/mensajes")
public class MensajeController{

    @Autowired
    private IMensajeService mensajeService;


    @GetMapping("")
    public String listarMensajes(Model model){
        List<Mensaje> mensajes = mensajeService.buscarTodos();

        model.addAttribute("titulo", "Mensajes");
        model.addAttribute("mensajes", mensajes);
        model.addAttribute("active", "mensajes");

        return "/mensajes/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarMensaje(@PathVariable Integer id, Model model){
        Mensaje mensaje = null;
        if(id > 0 ){
            mensaje = mensajeService.buscarPorId(id);
        }
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("active", "mensajes");
        model.addAttribute("titulo", "Editar mensaje");

        return "/mensajes/form-mensaje";
    }
    @PostMapping("/actualizar")
    public String actualizarMensaje(@Valid Mensaje mensaje, BindingResult result, 
                            RedirectAttributes flash, Model model, SessionStatus status){
        
        if(result.hasErrors()){
            model.addAttribute("titulo", "Editar mensaje");
            return "/mensaje/form-mensaje";
        }
        mensajeService.actualizar(mensaje);
        status.setComplete();

        return "redirect:/mensajes";
    }
    
}
