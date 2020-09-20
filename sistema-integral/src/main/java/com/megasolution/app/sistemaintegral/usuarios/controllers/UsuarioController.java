package com.megasolution.app.sistemaintegral.usuarios.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.megasolution.app.sistemaintegral.usuarios.models.entities.Rol;
import com.megasolution.app.sistemaintegral.usuarios.models.entities.Usuario;
import com.megasolution.app.sistemaintegral.usuarios.services.IRolService;
import com.megasolution.app.sistemaintegral.usuarios.services.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping("/usuarios")
@SessionAttributes("usuario")
public class UsuarioController {
    
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    

    @GetMapping("")
    public String listarUsuarios(Model model){

        List<Usuario> usuarios = usuarioService.buscarTodos();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("titulo", "Usuarios");
        model.addAttribute("active", "usuarios");

        return "usuarios/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model, RedirectAttributes flash){
        List<Rol> roles = rolService.buscarTodos();
        
        if(usuarioService.buscarPorId(id) == null){
            flash.addFlashAttribute("error", "El usuario no existe!");
            return "redirect:/usuarios";
        }
        Usuario usuario = null;
        if(id > 1){
            usuario = usuarioService.buscarPorId(id);
            
        }else{
            model.addAttribute("titulo", "Editar Usuario");
            model.addAttribute("roles", roles);
            model.addAttribute("usuario", usuario);
            model.addAttribute("active", "usuarios");
            return "redirect:/usuarios";
        }
        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("roles", roles);
        model.addAttribute("usuario", usuario);
        model.addAttribute("active", "usuarios");

        return "usuarios/form-usuario";
    }
    
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model){
        Usuario usuario = new Usuario();
        List<Rol> roles = rolService.buscarTodos();

        usuario.setFechaAlta(new Date());

        model.addAttribute("titulo", "Agregar Usuario");
        model.addAttribute("active", "usuarios");
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);

        return "usuarios/form-usuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash){
        List<Rol> roles = rolService.buscarTodos();
        if(usuarioService.buscarPorNombreUsuario(usuario.getNombreUsuario()) != null && usuario.getId() == null){
            model.addAttribute("alertDangerNombreUsuario", " alert-danger");
            model.addAttribute("errorNombreUsuario", "Este usuario ya existe!");
            model.addAttribute("roles", roles);
            return "usuarios/form-usuario";
        }
        if(result.hasErrors()){
            if(usuario.getId() == null){
                model.addAttribute("titulo", "Agregar Usuario");
                model.addAttribute("active", "usuarios");
            }else{
                model.addAttribute("titulo", "Editar Usuario");
                model.addAttribute("active", "usuarios");
            }
            model.addAttribute("roles", roles);
            return "usuarios/form-usuario";
        }

        usuario.setContraseña(this.passwordEncoder.encode(usuario.getContraseña()));

        if(usuario.getId() == null){
            usuarioService.guardar(usuario);
            status.setComplete();
            flash.addFlashAttribute("success", "Usuario guardado con éxito!");
        }else{
            usuarioService.guardar(usuario);
            status.setComplete();
            flash.addFlashAttribute("success", "Usuario actualizado con éxito!");
        }

        
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes flash){
        Usuario usuario = usuarioService.buscarPorId(id);
        if(usuarioService.buscarPorId(id) == null ||  usuario.getId() == 1){            
            flash.addFlashAttribute("error", "El usuario no existe!");
            return "redirect:/usuarios";
        }
        usuarioService.eliminar(id);
        flash.addFlashAttribute("success", "Usuario eliminado con éxito!");
        return "redirect:/usuarios";
    }
}
