package com.megasolution.app.sistemaintegral.clientes.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.clientes.models.entities.Localidad;
import com.megasolution.app.sistemaintegral.clientes.models.entities.Pais;
import com.megasolution.app.sistemaintegral.clientes.models.entities.Provincia;
import com.megasolution.app.sistemaintegral.clientes.services.IClienteService;
import com.megasolution.app.sistemaintegral.clientes.services.ILocalidadService;
import com.megasolution.app.sistemaintegral.clientes.services.IPaisService;
import com.megasolution.app.sistemaintegral.clientes.services.IProvinciaService;

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
@SessionAttributes("cliente")
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ILocalidadService localidadService;

    @Autowired
    private IProvinciaService provinciaService;

    @Autowired
    private IPaisService paisService;

    @GetMapping("")
    public String listarClientes(Model model){
        List<Cliente> clientes = clienteService.buscarTodos();

        model.addAttribute("titulo", "Clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("active", "clientes");

        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        Cliente cliente = new Cliente();
        
        cliente.setFechaAlta(new Date());

        List<Localidad> localidades = localidadService.buscarTodos();
        List<Provincia> provincias = provinciaService.buscarTodos();
        List<Pais> paises = paisService.buscarTodos();
        

        model.addAttribute("titulo", "Agregar cliente");
        model.addAttribute("cliente", cliente);
        model.addAttribute("active", "clientes");
        model.addAttribute("localidades", localidades);
        model.addAttribute("provincias", provincias);
        model.addAttribute("paises", paises);
        return "clientes/form-cliente";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Cliente cliente, BindingResult result, 
                            RedirectAttributes flash, Model model, SessionStatus status){
        List<Localidad> localidades = localidadService.buscarTodos();
        List<Provincia> provincias = provinciaService.buscarTodos();
        List<Pais> paises = paisService.buscarTodos();

        if(clienteService.buscarPorDniCuit(cliente.getDniCuit()) != null){
            model.addAttribute("alertDangerDniCuit", " alert-danger");
            model.addAttribute("errorDniCuit", "Ya existe un cliente con este número de documento!");
            model.addAttribute("localidades", localidades);
            model.addAttribute("provincias", provincias);
            model.addAttribute("paises", paises);
            return "clientes/form-cliente";
        }
        if(result.hasErrors()){
            if(cliente.getId() == null){
                model.addAttribute("titulo", "Agregar Cliente");
                model.addAttribute("active", "clientes"); 
            }else{
                model.addAttribute("titulo", "Editar Cliente");
                model.addAttribute("active", "clientes"); 
            }
            model.addAttribute("localidades", localidades);
            model.addAttribute("provincias", provincias);
            model.addAttribute("paises", paises);
            return "clientes/form-cliente";
        }
        if(cliente.getId() == null){
            clienteService.guardar(cliente);
            status.setComplete();
            flash.addFlashAttribute("success", "Cliente guardado con éxito!");  
        }else{
            clienteService.guardar(cliente);
            status.setComplete();
            flash.addFlashAttribute("success", "Cliente actualizado con éxito!");  
        }
        
        
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){
            flash.addFlashAttribute("error", "El cliente no existe!"); 
            return "redirect:/clientes";
        }
        Cliente cliente = null;
        if(id > 0 ){
            cliente = clienteService.buscarPorId(id);
        }
        List<Localidad> localidades = localidadService.buscarTodos();
        List<Provincia> provincias = provinciaService.buscarTodos();
        List<Pais> paises = paisService.buscarTodos();
        model.addAttribute("cliente", cliente);
        model.addAttribute("active", "clientes");
        model.addAttribute("titulo", "Editar Cliente");
        model.addAttribute("localidades", localidades);
        model.addAttribute("provincias", provincias);
        model.addAttribute("paises", paises);
        return "clientes/form-cliente";
    }
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){
            flash.addFlashAttribute("error", "El cliente no existe!"); 
            return "redirect:/clientes";
        }
        Cliente cliente = null;
        if(id > 0 ){
            cliente = clienteService.buscarPorId(id);
        }
        List<Localidad> localidades = localidadService.buscarTodos();
        List<Provincia> provincias = provinciaService.buscarTodos();
        List<Pais> paises = paisService.buscarTodos();
        // clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("active", "clientes");
        model.addAttribute("titulo", "Ver Cliente");
        model.addAttribute("localidades", localidades);
        model.addAttribute("provincias", provincias);
        model.addAttribute("paises", paises);
        return "clientes/form-cliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){
            flash.addFlashAttribute("error", "El cliente no existe!"); 
            return "redirect:/clientes";
        }
        clienteService.eliminar(id);
        flash.addFlashAttribute("success", "Cliente eliminado con éxito!"); 
        return "redirect:/clientes";
    }

}
