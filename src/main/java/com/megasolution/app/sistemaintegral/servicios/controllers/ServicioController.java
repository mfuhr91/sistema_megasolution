package com.megasolution.app.sistemaintegral.servicios.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.megasolution.app.sistemaintegral.avisos.models.entities.Aviso;
import com.megasolution.app.sistemaintegral.avisos.models.entities.Llamado;
import com.megasolution.app.sistemaintegral.avisos.models.entities.Mensaje;
import com.megasolution.app.sistemaintegral.avisos.services.IAvisoService;
import com.megasolution.app.sistemaintegral.avisos.services.ILlamadoService;
import com.megasolution.app.sistemaintegral.avisos.services.IMensajeService;
import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.clientes.services.IClienteService;
import com.megasolution.app.sistemaintegral.sectores.models.entities.Sector;
import com.megasolution.app.sistemaintegral.sectores.services.ISectorService;
import com.megasolution.app.sistemaintegral.servicios.models.entities.Estado;
import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.servicios.services.IEstadoService;
import com.megasolution.app.sistemaintegral.servicios.services.IServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private IAvisoService avisoService;

    @Autowired
    private IMensajeService mensajeService;

    @Autowired
    private ISectorService sectorService;

    @Autowired
    private ILlamadoService llamadoService;

    @GetMapping("")
    public String listarServicios(Model model){
        List<Servicio> servicios = servicioService.buscarTodos(); 
        
        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", servicios);
        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "todos");
        return "servicios/lista";
    }

    @GetMapping("/pendiente")
    public String listarPendientes(Model model){
        List<Servicio> serviciosPendientes = servicioService.buscarPorEstadoServicio(1);
        
        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosPendientes);
        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "pendiente");

        return "servicios/lista";
    }
    @GetMapping("/en-proceso")
    public String listarEnProceso(Model model){
        List<Servicio> serviciosEnProceso = servicioService.buscarPorEstadoServicio(2);
        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosEnProceso);

        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "en_proceso");

        return "servicios/lista";
    }
    @GetMapping("/terminado")
    public String listarTerminado(Model model){
        List<Servicio> serviciosTerminados = servicioService.buscarPorEstadoServicio(3);
        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosTerminados);

        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "terminado");

        return "servicios/lista";
    }
    @GetMapping("/entregado")
    public String listarEntregado(Model model){
        List<Servicio> serviciosEntragados = servicioService.buscarPorEstadoServicio(4);
        model.addAttribute("titulo", "Servicios");
        model.addAttribute("servicios", serviciosEntragados);

        model.addAttribute("active", "servicios");
        model.addAttribute("pill_activo", "entregado");

        return "servicios/lista";
    }
    @GetMapping("/cliente/{id}")
    public String serviciosPorCliente(@PathVariable Integer id,Model model,RedirectAttributes flash) {
        
        if(clienteService.buscarPorId(id) == null){
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorServicioConClienteId(cliente.getId());

        model.addAttribute("servicios", servicios);
        model.addAttribute("titulo", "Servicios del cliente " + cliente.getRazonSocial());
        model.addAttribute("active", "servicios");
        model.addAttribute("cliente", cliente);
        model.addAttribute("pill_activo", "todos");

        return "clientes/lista-servicios";
    }

    @GetMapping("/pendiente/cliente/{id}")
    public String listarPendientesCliente(@PathVariable Integer id,Model model, RedirectAttributes flash){
        if(clienteService.buscarPorId(id) == null){
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(1, cliente.getId());
        
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
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(2, cliente.getId());
    
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
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(3, cliente.getId());
        
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
            flash.addFlashAttribute("error","El cliente no existe!");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(4, cliente.getId());
        
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
            flash.addFlashAttribute("error","El servicio no existe!");
            return "redirect:/servicios";
        }
        Servicio servicio = null;
        Cliente cliente = null;
        Sector sector = null;
        List<Estado> estados = estadoService.buscarTodos();
        List<Sector> sectores = sectorService.buscarDisponibles();
        List<Cliente> clientes = clienteService.buscarTodos();
        if(id > 0){
            servicio = servicioService.buscarPorId(id);
            cliente = clienteService.buscarPorId(servicio.getCliente().getId());
        }
        if(servicio.getSector() != null){
            sector = servicio.getSector();
        }
        if(sector != null){
            model.addAttribute("sector", sector.getNombre());
        }
        model.addAttribute("cliente_id", cliente.getId());
        model.addAttribute("servicio", servicio);
        model.addAttribute("cliente", cliente.getDniCuit() + " - " + cliente.getRazonSocial());
        model.addAttribute("estados", estados);
        model.addAttribute("sectores", sectores);
        model.addAttribute("clientes", clientes);
        model.addAttribute("active", "servicios");
        model.addAttribute("titulo", "Editar Servicio");
        return "servicios/form-servicio";
    }
    
    // MAPEA A LA CLASE ImprimirServicio.java PARA GENERAR EL PDF
    @GetMapping("/imprimir/{id}")
    public String imprimirServicio(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if(servicioService.buscarPorId(id) == null){
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
        servicio.setCargador(true);
        servicio.setBateria(true);
        servicio.setFechaIngreso(new Date());
        model.addAttribute("titulo", "Agregar Servicio");
        model.addAttribute("active", "servicios");
        model.addAttribute("servicio", servicio);
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
        List<Cliente> clientes = clienteService.buscarTodos();
        List<Sector> sectores = sectorService.buscarDisponibles();
       
        // VALIDA QUE SE HAYA SELECIONADO UN CLIENTE SINO LANZA UN ERROR AL USUARIO
        if(servicio.getCliente().getId() == null){
            model.addAttribute("errorCliente", "Debe seleccionar un cliente antes de guardar!");
            model.addAttribute("alertDangerCliente", " form-control alert-danger");
            if(servicio.getId() == null){
                model.addAttribute("titulo", "Agregar Servicio");
            }else{
                model.addAttribute("titulo", "Editar Servicio");
            }
            model.addAttribute("active", "servicio");
            model.addAttribute("clientes", clientes);
            model.addAttribute("estados", estados);
            model.addAttribute("sectores", sectores);
            servicioService.recuperarEstadoTerminado(servicio);
            if(servicio.getEstado().getId() == 3 && servicio.getSolucion().isEmpty()){
                model.addAttribute("errorSolucion", "Debe ingresar una solución antes de guardar el servicio terminado!");
                model.addAttribute("alertDangerSolucion", " form-control alert-danger");
            }
            if(servicio.getEstado().getId() == 4 && servicio.getSolucion().isEmpty()){
                model.addAttribute("errorSolucion", "Debe ingresar una solución antes de guardar el servicio como entregado!");
                model.addAttribute("alertDangerSolucion", " form-control alert-danger");
            }
            if(servicio.getSector().getId() == null){
                model.addAttribute("errorSector", "Debe seleccionar un sector antes de guardar!");
                model.addAttribute("alertDangerSector", " form-control alert-danger");
            }else{
                Sector sector = sectorService.buscarPorId(servicio.getSector().getId());
                model.addAttribute("sector", sector.getNombre());
            }
            
            return "servicios/form-servicio";
        }
        Cliente cliente = clienteService.buscarPorId(servicio.getCliente().getId());
        
        // VALIDA QUE SE HAYA SELECCIONADO UN SECTOR SINO LANZA UN ERROR
        if(servicio.getSector().getId() == null){
            model.addAttribute("errorSector", "Debe seleccionar un sector antes de guardar!");
            model.addAttribute("alertDangerSector", " form-control alert-danger");
            if(servicio.getId() == null){
                model.addAttribute("titulo", "Agregar Servicio");
            }else{
                model.addAttribute("titulo", "Editar Servicio");
            }
            model.addAttribute("active", "servicio");
            model.addAttribute("clientes", clientes);
            model.addAttribute("estados", estados);
            model.addAttribute("sectores", sectores);
            servicioService.recuperarEstadoTerminado(servicio);
            model.addAttribute("cliente", cliente.getDniCuit() + " - " + cliente.getRazonSocial());
            if(servicio.getEstado().getId() == 3 && servicio.getSolucion().isEmpty()){
                model.addAttribute("errorSolucion", "Debe ingresar una solución antes de guardar el servicio terminado!");
                model.addAttribute("alertDangerSolucion", " form-control alert-danger");
            }
            return "servicios/form-servicio";
        }
        Sector sector = sectorService.buscarPorId(servicio.getSector().getId());

        // VALIDA SI EL ESTADO DEL SERVICIO ES TERMINADO PERO NO SE INDICÓ UNA SOLUCIÓN
        if(servicio.getEstado().getId() == 3 && servicio.getSolucion().isEmpty()){
            model.addAttribute("errorSolucion", "Debe ingresar una solución antes de guardar el servicio terminado!");
            model.addAttribute("alertDangerSolucion", " form-control alert-danger");
            if(servicio.getId() == null){
                model.addAttribute("titulo", "Agregar Servicio");
            }else{
                model.addAttribute("titulo", "Editar Servicio");
            }
            model.addAttribute("active", "servicio");
            model.addAttribute("clientes", clientes);
            model.addAttribute("estados", estados);
            model.addAttribute("sectores", sectores);
            model.addAttribute("cliente", cliente.getDniCuit() + " - " + cliente.getRazonSocial());
            model.addAttribute("sector", sector.getNombre());
            
            servicioService.recuperarEstadoTerminado(servicio);      
            return "servicios/form-servicio"; 
        }

        // VALIDA SI EL ESTADO DEL SERVICIO ES ENTREGADO PERO NO SE INDICÓ UNA SOLUCIÓN
        if(servicio.getEstado().getId() == 4 && servicio.getSolucion().isEmpty()){
            model.addAttribute("errorSolucion", "Debe ingresar una solución antes de guardar el servicio como entregado!");
            model.addAttribute("alertDangerSolucion", " form-control alert-danger");
            if(servicio.getId() == null){
                model.addAttribute("titulo", "Agregar Servicio");
            }else{
                model.addAttribute("titulo", "Editar Servicio");
            }
            model.addAttribute("active", "servicio");
            model.addAttribute("clientes", clientes);
            model.addAttribute("estados", estados);
            model.addAttribute("sectores", sectores);
            model.addAttribute("cliente", cliente.getDniCuit() + " - " + cliente.getRazonSocial());
            model.addAttribute("sector", sector.getNombre());
            
            servicioService.recuperarEstadoTerminado(servicio);      
            return "servicios/form-servicio"; 
        }
        // SI HAY ERRORES EN LA VALIDACION DE CAMPOS
        if(result.hasErrors()){
            if(servicio.getId() == null){
                model.addAttribute("titulo", "Agregar Servicio");
            }else{
                model.addAttribute("titulo", "Editar Servicio");
            }
            model.addAttribute("active", "servicio");
            model.addAttribute("clientes", clientes);
            model.addAttribute("estados", estados);
            model.addAttribute("sectores", sectores);
            model.addAttribute("cliente", cliente.getDniCuit() + " - " + cliente.getRazonSocial());
            model.addAttribute("sector", sector.getNombre());
            
            servicioService.recuperarEstadoTerminado(servicio); 
            
            return "servicios/form-servicio";
        }
        // ESTADO DEL SERVICIO CAMBIA A TERMINADO = ID 3
        Aviso aviso = new Aviso();
        Aviso avisoBuscado = new Aviso();
        if(servicio.getEstado().getId() == 3){
            Llamado llamado = llamadoService.buscarPorId(1);
            Mensaje mensaje = mensajeService.buscarPorId(1);
            aviso = new Aviso(mensaje.getTipoMensaje(), mensaje, servicio, llamado);
            avisoBuscado = avisoService.buscarAvisoPorServicioId(servicio.getId());
            
                
            sector.setDisponible(false);
    
        
        // EN CASO QUE ESTADO DE SERVICIO SEA 1 o 2
        }else{
            aviso = avisoService.buscarAvisoPorServicioId(servicio.getId());
            if(aviso != null){
                avisoService.eliminar(aviso.getId());
                servicio.setAviso(null);
            }
        
            sector.setDisponible(false);
        }
        // ESTADO DEL SERVICIO CAMBIA A ENTREGADO = ID 4
        if(servicio.getEstado().getId() == 4){
            aviso = avisoService.buscarAvisoPorServicioId(servicio.getId());

            if(aviso == null){
                servicio.setSector(null);
                sector.setDisponible(true);    
                sectorService.guardar(sector);
            }else{
                
                avisoService.eliminar(aviso.getId());
                servicio.setSector(null);
                servicio.setAviso(null);
                sector.setDisponible(true);
                sectorService.guardar(sector);
            } 
        }
        
        model.addAttribute("titulo", "Agregar Servicio");
        model.addAttribute("active", "servicio");
        
        if(servicio.getId() != null){
            servicioService.guardar(servicio);
            status.setComplete();
            flash.addFlashAttribute("success", "Servicio actualizado con éxito!");
                        if(avisoBuscado == null){
                            avisoService.guardar(aviso);  
                        } 
            return "redirect:/servicios";
        }else{
            servicioService.guardar(servicio);
            status.setComplete();
            flash.addFlashAttribute("successNuevo", "Servicio guardado con éxito!");
            flash.addFlashAttribute("servicioId", servicio.getId());
                            if(avisoBuscado == null){
                                avisoService.guardar(aviso);  
                            } 
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

}
