package com.megasolution.app.sistemaintegral.usuarios.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    @GetMapping("")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash){
                            
        if(principal != null){ // si principal tiene un usuario, es xq ya inicio sesion antes
            flash.addFlashAttribute("info", "Ya ha iniciado sesión!");
            return "redirect:/";
        }
        if(error != null){
            model.addAttribute("error", "El usuario o la contraseña son incorrectos!"); /* "El usuario o la contraseña son incorrectos!" */
        }
        if(logout != null){
            model.addAttribute("success", "Has cerrado sesión con exito!");
            
        }
        model.addAttribute("titulo", "Ingreso al sistema");
        return "login";
    }


}
