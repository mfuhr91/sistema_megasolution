package com.megasolution.app.sistemaintegral.controllers;

import com.megasolution.app.sistemaintegral.models.ClienteModel;
import com.megasolution.app.sistemaintegral.models.Provincias;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.respuestaJson.Localidad;
import com.megasolution.app.sistemaintegral.services.ClienteService;
import com.megasolution.app.sistemaintegral.utils.Constantes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Controller
@SessionAttributes(Constantes.CLIENTE)
@RequestMapping(Constantes.CLIENTES)
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping()
    public String listar100(Model model) throws IOException {
        List<Cliente> clientes = clienteService.buscar100();
        ClienteModel clienteModel = new ClienteModel(clientes);
        model.addAttribute(this.clienteService.enviarModelo(clienteModel,model));
        return Constantes.TEMPLATE_LISTA_CLIENTES;
    }

    @GetMapping(Constantes.TODOS)
    public String listarClientes(Model model) throws IOException {
        List<Cliente> clientes = clienteService.buscarTodos();
        ClienteModel clienteModel = new ClienteModel(clientes);
        model.addAttribute(this.clienteService.enviarModelo(clienteModel,model));
        return Constantes.TEMPLATE_LISTA_CLIENTES;
    }

    @GetMapping(Constantes.NUEVO)
    public String nuevo(Model model) throws IOException {
        Cliente cliente = new Cliente();
        cliente.setFechaAlta(LocalDate.now());
        ClienteModel clienteModel = new ClienteModel(cliente);
        model.addAttribute(this.clienteService.enviarModelo(clienteModel,model));
        return Constantes.TEMPLATE_FORM_CLIENTES;
    }

    @PostMapping(Constantes.GUARDAR)
    public String guardar(@Valid Cliente cliente, BindingResult result, 
                            RedirectAttributes flash, Model model, SessionStatus status) throws IOException {
        ClienteModel clienteModel = new ClienteModel(cliente);
        if(cliente.getId() != null){ 
            Cliente clienteBuscado = clienteService.buscarPorDniCuit(cliente.getDniCuit());
            if(clienteBuscado != null){
                if(cliente.getId() != null && clienteBuscado.getDniCuit().equals(cliente.getDniCuit()) 
                                            && !clienteBuscado.getId().equals(cliente.getId())){
                    model.addAttribute(clienteService.enviarModelo(clienteModel, model));
                    model.addAttribute(clienteService.enviarModeloErrorDniCuit(clienteModel, model));
                    return Constantes.TEMPLATE_FORM_CLIENTES;
                }
            }
        }
        if(cliente.getId() == null && clienteService.buscarPorDniCuit(cliente.getDniCuit()) != null){
            model.addAttribute(clienteService.enviarModelo(clienteModel, model));
            model.addAttribute(clienteService.enviarModeloErrorDniCuit(clienteModel, model));
            return Constantes.TEMPLATE_FORM_CLIENTES;
        }
        if(result.hasErrors()){
            return Constantes.TEMPLATE_FORM_CLIENTES;
        }
        if(cliente.getId() == null){
            clienteService.guardar(cliente);
            status.setComplete();
            flash.addFlashAttribute(Constantes.SUCCESS, Constantes.MSJ_CLIENTE_GUARDADO);
        }else{
            clienteService.guardar(cliente);
            status.setComplete();
            flash.addFlashAttribute(Constantes.SUCCESS, Constantes.MSJ_CLIENTE_ACTUALIZADO);
        }

        return Constantes.REDIRECT_CLIENTES;
    }

    @GetMapping(Constantes.EDITAR_ID)
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes flash) throws IOException {
        if(clienteService.buscarPorId(id) == null){
            flash.addFlashAttribute(Constantes.ERROR, Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        Cliente cliente = null;
        if(!ObjectUtils.isEmpty(id) ){
            cliente = clienteService.buscarPorId(id);
        }
        ClienteModel clienteModel = new ClienteModel(cliente);
        model.addAttribute(this.clienteService.enviarModelo(clienteModel,model));
        return Constantes.TEMPLATE_FORM_CLIENTES;
    }

    @GetMapping(Constantes.ELIMINAR_ID)
    public String eliminar(@PathVariable Integer id, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){
            flash.addFlashAttribute(Constantes.ERROR, Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        clienteService.eliminar(id);
        flash.addFlashAttribute(Constantes.SUCCESS, Constantes.MSJ_CLIENTE_ELIMINADO);
        return Constantes.REDIRECT_CLIENTES;
    }

    @GetMapping(Constantes.BUSCAR)
    public String buscarClientes(@RequestParam String param, Model model, RedirectAttributes flash) throws IOException {
        if(StringUtils.isEmpty(param)){
            return Constantes.REDIRECT_CLIENTES;
        }
        List<Cliente> clientes = this.clienteService.buscarPorParametro(param);
        if(!clientes.isEmpty()){
            ClienteModel clienteModel = new ClienteModel(clientes);
            model.addAttribute(this.clienteService.enviarModelo(clienteModel,model));
            return Constantes.TEMPLATE_LISTA_CLIENTES;
        } else {
            flash.addFlashAttribute(Constantes.WARNING, Constantes.MSJ_CLIENTE_NO_ENCONTRADO);
            return Constantes.REDIRECT_CLIENTES;
        }
    }

    @GetMapping(Constantes.GET_CIUDADES)
    public @ResponseBody List<Localidad> getCiudades(@PathVariable String provincia) throws IOException {
        return Provincias.getCiudadesDeProvincia(provincia);
    }

    @GetMapping(Constantes.EDITAR_GET_CIUDADES)
    public @ResponseBody List<Localidad> getCiudadesEditar(@PathVariable String provincia) throws IOException {
        return Provincias.getCiudadesDeProvincia(provincia);
    }
}
