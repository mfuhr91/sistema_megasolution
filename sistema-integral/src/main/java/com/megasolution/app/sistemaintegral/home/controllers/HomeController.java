package com.megasolution.app.sistemaintegral.home.controllers;


import com.megasolution.app.sistemaintegral.clientes.services.IClienteService;

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

    @GetMapping({"","/","/home","/index","/inicio"})
    public String inicio(Model model){

        Integer totalClientes = clienteService.contarClientes();
        model.addAttribute("titulo", "Inicio");
        model.addAttribute("active", "inicio");
        model.addAttribute("totalClientes", totalClientes);
        return "/inicio";
    }
}
