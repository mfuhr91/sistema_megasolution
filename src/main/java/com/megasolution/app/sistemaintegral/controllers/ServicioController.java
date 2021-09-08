package com.megasolution.app.sistemaintegral.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Estado;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.services.IClienteService;
import com.megasolution.app.sistemaintegral.services.IEstadoService;
import com.megasolution.app.sistemaintegral.services.ISectorService;
import com.megasolution.app.sistemaintegral.services.IServicioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/servicios")
@SessionAttributes("servicio")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IEstadoService estadoService;

    @Autowired
    private ISectorService sectorService;

    private final Logger log = LoggerFactory.getLogger(ServicioController.class);

    @GetMapping("")
    public String listar50Ultimos(Model model){
        List<Servicio> servicios = servicioService.buscar50Ultimos(); 
        
        log.info("ultimos 50 servicios listados");

        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", servicios);
        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "todos");
        return "servicios/lista";
    }
    @GetMapping("/todos")
    public String listarServicios(Model model){
        List<Servicio> servicios = servicioService.buscarTodos();

        log.info("servicios listados");

        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", servicios);
        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "todos");
        return "servicios/lista";
    }

    @GetMapping("/pendiente")
    public String listarPendientes(Model model){
        List<Servicio> serviciosPendientes = servicioService.buscarPorEstadoServicio(Estado.PENDIENTE);

        log.info("servicios pendientes listados");
        
        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosPendientes);
        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "pendiente");

        return "servicios/lista";
    }
    @GetMapping("/en-proceso")
    public String listarEnProceso(Model model){
        List<Servicio> serviciosEnProceso = servicioService.buscarPorEstadoServicio(Estado.EN_PROCESO);

        log.info("servicios en proceso listados");

        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosEnProceso);

        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "en_proceso");

        return "servicios/lista";
    }
    @GetMapping("/terminado")
    public String listarTerminado(Model model){
        List<Servicio> serviciosTerminados = servicioService.buscarPorEstadoServicio(Estado.TERMINADO);

        log.info("servicios terminados listados");

        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosTerminados);

        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "terminado");

        return "servicios/lista";
    }
    @GetMapping("/entregado")
    public String listarEntregado(Model model){
        List<Servicio> serviciosEntregados = servicioService.buscarPorEstadoServicio(Estado.ENTREGADO);

        log.info("servicios entregados listados");

        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosEntregados);
        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "entregado");

        return "servicios/lista";
    }
    @GetMapping("/guardado")
    public String listarGuardado(Model model){
        List<Servicio> serviciosGuardados = servicioService.buscarPorEstadoServicio(Estado.GUARDADO);

        log.info("servicios guardado listados");

        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosGuardados);
        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "guardado");

        return "servicios/lista";
    }
    @GetMapping("/cliente/{id}")
    public String serviciosPorCliente(@PathVariable Integer id,Model model,RedirectAttributes flash) {
        
        if(clienteService.buscarPorId(id) == null){

            log.error("el cliente no existe");

            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorServicioConClienteId(cliente.getId());

        log.info("servicios del cliente: ".concat(cliente.getRazonSocial()).concat(" listados"));

        model.addAttribute("servicios", servicios);
        model.addAttribute("titulo", "Servicios del cliente " + cliente.getRazonSocial());
        model.addAttribute("active", "servicios");
        model.addAttribute("cliente", cliente);
        model.addAttribute("pill_activo", "todos");

        return "servicios/lista";
    }

    @GetMapping("/pendiente/cliente/{id}")
    public String listarPendientesCliente(@PathVariable Integer id,Model model, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){

            log.error("el cliente no existe");
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(1, cliente.getId());
        log.info("servicios pendientes del cliente: ".concat(cliente.getRazonSocial()).concat(" listados"));
        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", servicios);
        model.addAttribute("active", "servicios");

        
        model.addAttribute("cliente", cliente);
        model.addAttribute("pill_activo", "pendiente");

        return "clientes/lista-servicios";
    }
    @GetMapping("/en-proceso/cliente/{id}")
    public String listarEnProcesoCliente(@PathVariable Integer id,Model model, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){

            log.error("el cliente no existe");
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(2, cliente.getId());
        log.info("servicios en proceso del cliente: ".concat(cliente.getRazonSocial()).concat(" listados"));
        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", servicios);
        model.addAttribute("active", "servicios");

        model.addAttribute("cliente", cliente);
        model.addAttribute("pill_activo", "en_proceso");

        return "clientes/lista-servicios";
    }
    @GetMapping("/terminado/cliente/{id}")
    public String listarTerminadoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){

            log.error("el cliente no existe");
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(3, cliente.getId());
        log.info("servicios terminados del cliente: ".concat(cliente.getRazonSocial()).concat(" listados"));

        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", servicios);
        model.addAttribute("active", "servicios");

    
        model.addAttribute("cliente", cliente);
        model.addAttribute("pill_activo", "terminado");

        return "clientes/lista-servicios";
    }
    
    @GetMapping("/entregado/cliente/{id}")
    public String listarEntregadoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){
            log.error("el cliente no existe");
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(4, cliente.getId());
        log.info("servicios entregados del cliente: ".concat(cliente.getRazonSocial()).concat(" listados"));

        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", servicios);
        model.addAttribute("active", "servicios");

    
        model.addAttribute("cliente", cliente);
        model.addAttribute("pill_activo", "entregado");

        return "clientes/lista-servicios";
    }

    @GetMapping("/editar/{id}")
    public String editarServicio(@PathVariable Integer id, Model model, RedirectAttributes flash){
    
        if(servicioService.buscarPorId(id) == null){
            log.error("el servicio no existe");

            flash.addFlashAttribute("error","El servicio no existe!");
            return "redirect:/servicios";
        }
        Servicio servicio = null;
        Cliente cliente = null;
        List<Estado> estados = estadoService.buscarTodos();
        estados = estados.stream()
                         .filter(estado -> !estado.getCodigo().equals(Estado.ENVIADO) && !estado.getCodigo().equals(Estado.NO_ENVIADO))
                         .collect(Collectors.toList());
        List<Sector> sectores = sectorService.buscarDisponibles();
        if(id > 0){
            servicio = servicioService.buscarPorId(id);
            cliente = clienteService.buscarPorId(servicio.getCliente().getId());
        }
        if(servicio.getSector() != null){
            model.addAttribute("sector", servicio.getSector().getNombre());
        }
        model.addAttribute("servicio", servicio);
        model.addAttribute("cliente", cliente.getDniCuit() + " - " + cliente.getRazonSocial());
        model.addAttribute("servicioId", servicio.getId());
        model.addAttribute("telefono", cliente.getTelefono());
        model.addAttribute("estados", estados);
        model.addAttribute("sectores", sectores);
        model.addAttribute("active", "servicios");
        model.addAttribute("titulo", "Editar Servicio");
        return "servicios/form-servicio";
    }
    
    // MAPEA A LA CLASE ImprimirServicio.java PARA GENERAR EL PDF
    @GetMapping("/imprimir/{id}")
    public String imprimirServicio(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if(servicioService.buscarPorId(id) == null){
            log.error("el servicio no existe");
            flash.addFlashAttribute("error","El servicio no existe!");
            return "redirect:/servicios";
        }
        Servicio servicio = null;
        if(id > 0){
            servicio = servicioService.buscarPorId(id);
           
        }
        model.addAttribute("servicio", servicio);
        return "/servicios/form-servicio"; // debe quedar con el "/servicios/form-servicios" ya que no es una vista html, sino la ruta de un componente clase
    }

    @GetMapping("/nuevo")
    public String nuevoServicio(Model model){
        Servicio servicio = new Servicio();
        List<Estado> estados = estadoService.buscarTodos();
        estados = estados.stream()
                         .filter(estado -> estado.getCodigo().equals(Estado.PENDIENTE) || estado.getCodigo().equals(Estado.EN_PROCESO))
                         .collect(Collectors.toList());

        servicio.setCargador(true);
        servicio.setBateria(true);
        servicio.setFechaIngreso(new Date());
        model.addAttribute("titulo", "Agregar Servicio");
        model.addAttribute("active", "servicios");
        model.addAttribute("servicio", servicio);
        List<Sector> sectores = sectorService.buscarDisponibles();
  
        model.addAttribute("sectores", sectores);

        model.addAttribute("estados", estados);
       
        return "servicios/form-servicio";
    }

    @GetMapping("/nuevo/cliente/{id}")
    public String nuevoServicioCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){

        if(clienteService.buscarPorId(id) == null){
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        Servicio servicio = new Servicio();
        List<Estado> estados = estadoService.buscarTodos();
        estados = estados.stream()
                         .filter(estado -> estado.getCodigo().equals(Estado.PENDIENTE) || estado.getCodigo().equals(Estado.EN_PROCESO))
                         .collect(Collectors.toList());
        servicio.setCargador(true);
        servicio.setBateria(true);
        servicio.setFechaIngreso(new Date());
        model.addAttribute("titulo", "Agregar Servicio");
        model.addAttribute("active", "servicios");
        model.addAttribute("servicio", servicio);

        model.addAttribute("id", id);
        model.addAttribute("cliente", cliente.getDniCuit() + " - " + cliente.getRazonSocial());
        model.addAttribute("telefono", cliente.getTelefono());

        
        List<Sector> sectores = sectorService.buscarDisponibles();
        List<Cliente> clientes = clienteService.buscarTodos();
        model.addAttribute("sectores", sectores);
        model.addAttribute("clientes", clientes);

        model.addAttribute("estados", estados);
       
        return "servicios/form-servicio";
    }

    @PostMapping("/guardar")
    public String guardarServicio(@Valid Servicio servicio, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash){
        List<Estado> estados = estadoService.buscarTodos();
        estados = estados.stream()
                        .filter(estado -> !estado.getCodigo().equals(Estado.ENVIADO) && !estado.getCodigo().equals(Estado.NO_ENVIADO))
                        .collect(Collectors.toList());
    
        List<Sector> sectores = sectorService.buscarDisponibles();
        Sector sector = new Sector();
        Cliente cliente = new Cliente(); 

        if(servicio.getId() == null){
            model.addAttribute("titulo", "Agregar Servicio");

        }else{
            model.addAttribute("titulo", "Editar Servicio");
        }
        model.addAttribute("active", "servicio");
        
        model = this.servicioService.validarForm(servicio, model);

        if(    model.getAttribute("errorSolucion") != null 
            || model.getAttribute("errorCliente") != null 
            || model.getAttribute("errorSector") != null ) {

            if(servicio.getCliente().getId() != null){
                cliente = clienteService.buscarPorId(servicio.getCliente().getId());
            }
            if(servicio.getSector().getId() != null) {
                sector = sectorService.buscarPorId(servicio.getSector().getId());
                model.addAttribute("sector", sector.getNombre());
            }
            this.servicioService.enviarModelo(cliente, sectores, estados, sector, servicio, model);
            return "servicios/form-servicio";
        } else {
            cliente = clienteService.buscarPorId(servicio.getCliente().getId());
            sector = sectorService.buscarPorId(servicio.getSector().getId());
            model.addAttribute("sector", sector.getNombre());

        }

        // SI HAY ERRORES EN LA VALIDACION DE CAMPOS
        if(result.hasErrors()){
            this.servicioService.enviarModelo(cliente, sectores, estados, sector, servicio, model);          
            return "servicios/form-servicio";
        }
        
        this.servicioService.crearAviso(servicio);   
        this.servicioService.asignarSector(servicio, sector);    
        
        if(servicio.getId() != null){
            sectorService.guardar(sector);
            servicioService.guardar(servicio);
            status.setComplete();
            flash.addFlashAttribute("success", "Servicio actualizado con éxito!");
            return "redirect:/servicios";
        }else{ 
            try{
                sectorService.guardar(sector);
                servicioService.guardar(servicio);
            }catch(Exception e){
                
                return "redirect:/servicios";
            }
            status.setComplete();
            flash.addFlashAttribute("successNuevo", "Servicio guardado con éxito!");
            flash.addFlashAttribute("servicioId", servicio.getId());
            return "redirect:/servicios";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if(servicioService.buscarPorId(id) == null){
            flash.addFlashAttribute("error", "El servicio no existe!");
            return "redirect:/servicios";
        }
        Servicio servicio = servicioService.buscarPorId(id);
        if(servicio.getSector() != null){
            Sector sector = sectorService.buscarPorId(servicio.getSector().getId());
            sector.setDisponible(true);
        }

        servicioService.eliminar(id);
        flash.addFlashAttribute("success", "Servicio eliminado con exito!");
        return "redirect:/servicios";

    }  

    @GetMapping("/monitor")
    public String monitor(Model model){
        List<Servicio> serviciosPendientes = servicioService.buscarPorEstadoServicioMonitor(1);
        List<Servicio> serviciosEnProceso = servicioService.buscarPorEstadoServicioMonitor(2);
        
        model.addAttribute("totalPendientes", serviciosPendientes.size());
        model.addAttribute("totalEnProceso", serviciosEnProceso.size());
        
        model.addAttribute("serviciosPendientes", serviciosPendientes);
        model.addAttribute("serviciosEnProceso", serviciosEnProceso);
        model.addAttribute("titulo", "Visualizador de Servicios");
        return "servicios/monitor";
    }

    @GetMapping("/mostrar-sectores")
    public @ResponseBody List<Sector> listaSectores(){
        List<Sector> sectores = sectorService.buscarDisponibles();
        return  sectores;
    }

    @GetMapping("/mostrar-clientes")
    public @ResponseBody List<Cliente> listaClientes(){
        List<Cliente> clientes = clienteService.buscarTodos();
        return  clientes;
    }

    @PostMapping("/buscar/{estado}")
    public String buscarServicio(@RequestParam String param,@PathVariable String estado, Model model, RedirectAttributes flash){
        
        if(param == ""){
            return "redirect:/servicios";
        }
        List<Servicio> servicios = this.servicioService.buscarPorParamEstado(param, estado);
        if(servicios.size() > 0){
            model.addAttribute("titulo", "Servicios");
            model.addAttribute("servicios", servicios);
            model.addAttribute("active", "servicios");
            model.addAttribute("pill_activo", estado);
            
            return "servicios/lista";
        } else {
            flash.addFlashAttribute("warning", "No se encontró ningún servicio!");
            
            return "redirect:/servicios";
        }
    } 

    @PostMapping("/buscar-clientes")
    public String buscarClientesEnServicioNuevo(@RequestParam String param, Model model, RedirectAttributes flash){
        
        if(param == ""){
            return "redirect:/servicios/nuevo";
        }
        List<Cliente> clientes = this.clienteService.buscarPorParametro(param);
        if(clientes.size() > 0){
            model.addAttribute("titulo", "Servicios");
            model.addAttribute("clientes", clientes);
            model.addAttribute("active", "servicios");
    
            this.nuevoServicio(model);
           
            return "servicios/form-servicio";
        } else {
            flash.addFlashAttribute("warning", "No se encontró ningún cliente!");
            
            return "redirect:/servicios/nuevo";
        }
    }  
    @PostMapping("/buscar-clientes/{id}")
    public String buscarClientesEnServicio(@RequestParam String param, @PathVariable Integer id, Model model, RedirectAttributes flash){
        

        if(param == ""){
            return "redirect:/servicios/editar/{id}";
        }
        List<Cliente> clientes = this.clienteService.buscarPorParametro(param);
        if(clientes.size() > 0){
            model.addAttribute("titulo", "Servicios");
            model.addAttribute("clientes", clientes);
            model.addAttribute("active", "servicios");
    
            this.editarServicio(id, model, flash);
            
            
            return "servicios/form-servicio";
        } else {
            flash.addFlashAttribute("warning", "No se encontró ningún cliente!");
            
            return "redirect:/servicios/editar/{id}";
        }
    }  
    

}
