package com.megasolution.app.sistemaintegral.controllers;

import java.util.List;

import javax.validation.Valid;

import com.megasolution.app.sistemaintegral.models.ServicioModel;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.utils.Constantes;
import com.megasolution.app.sistemaintegral.utils.Estado;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.services.IClienteService;
import com.megasolution.app.sistemaintegral.services.ISectorService;
import com.megasolution.app.sistemaintegral.services.IServicioService;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
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
@RequestMapping(Constantes.SERVICIOS)
@SessionAttributes(Constantes.SERVICIO)
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ISectorService sectorService;

    private final Logger LOG = LoggerFactory.getLogger(ServicioController.class);

    @GetMapping()
    public String listar50Ultimos(Model model){
        List<Servicio> servicios = servicioService.buscar50Ultimos(); 
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        LOG.info("ultimos 50 servicios listados");
        model.addAttribute(servicioService.enviarModelo(servicioModel, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }
    @GetMapping(Constantes.TODOS)
    public String listarServicios(Model model){
        List<Servicio> servicios = servicioService.buscarTodos();
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        LOG.info("todos los servicios listados");
        model.addAttribute(servicioService.enviarModelo(servicioModel, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }

    @GetMapping(Constantes.PENDIENTE)
    public String listarPendientes(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.PENDIENTE, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }
    @GetMapping(Constantes.EN_PROCESO)
    public String listarEnProceso(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.EN_PROCESO, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }
    @GetMapping(Constantes.TERMINADO)
    public String listarTerminado(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.TERMINADO, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }
    @GetMapping(Constantes.ENTREGADO)
    public String listarEntregado(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.ENTREGADO, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }
    @GetMapping(Constantes.GUARDADO)
    public String listarGuardado(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.GUARDADO, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }
    @GetMapping(Constantes.CLIENTE_ID)
    public String serviciosPorCliente(@PathVariable Integer id,Model model,RedirectAttributes flash) {
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,null, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }

    @GetMapping(Constantes.PENDIENTE_CLIENTE_ID)
    public String listarPendientesCliente(@PathVariable Integer id,Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.PENDIENTE, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }
    @GetMapping(Constantes.EN_PROCESO_CLIENTE_ID)
    public String listarEnProcesoCliente(@PathVariable Integer id,Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.EN_PROCESO, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }
    @GetMapping(Constantes.TERMINADO_CLIENTE_ID)
    public String listarTerminadoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.TERMINADO, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }

    @GetMapping(Constantes.ENTREGADO_CLIENTE_ID)
    public String listarEntregadoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.ENTREGADO, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }

    @GetMapping(Constantes.GUARDADO_CLIENTE_ID)
    public String listarGuardadoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.GUARDADO, model));
        return Constantes.TEMPLATE_LISTA_SERVICIOS;
    }

    @GetMapping(Constantes.EDITAR_ID)
    public String editarServicio(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(servicioService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_SERVICIO_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_SERVICIO_NO_EXISTE);
            return Constantes.REDIRECT_SERVICIOS;
        }
        ServicioModel servicioModel = new ServicioModel();
        if(id > 0){
            Servicio servicio = servicioService.buscarPorId(id);
            Sector sector = (Sector) Hibernate.unproxy(servicio.getSector());
            servicioModel.setServicio(servicio);
            servicioModel.setCliente(servicio.getCliente());
            this.servicioService.almacenarSectorAnterior(sector);
        }
        model.addAttribute(servicioService.enviarModelo(servicioModel, model));
        return Constantes.TEMPLATE_FORM_SERVICIOS;
    }
    
    // MAPEA A LA CLASE ImprimirServicio.java PARA GENERAR EL PDF
    @GetMapping(Constantes.IMPRIMIR_SERVICIO_ID)
    public String imprimirServicio(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(servicioService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_SERVICIO_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_SERVICIO_NO_EXISTE);
            return Constantes.REDIRECT_SERVICIOS;
        }
        if( !ObjectUtils.isEmpty(id)){
            model.addAttribute( Constantes.SERVICIO, servicioService.buscarPorId(id) );
        }
        // debe quedar con el "/servicios/form-servicios" ya que no es una vista html, sino la ruta de un componente clase
        return Constantes.PATH_CLASE_JAVA_PDF;
    }

    @GetMapping(Constantes.NUEVO)
    public String nuevoServicio(Model model){
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicio(new Servicio());
        model.addAttribute(servicioService.enviarModelo(servicioModel,model));
        return Constantes.TEMPLATE_FORM_SERVICIOS;
    }

    @GetMapping(Constantes.NUEVO_CLIENTE_ID)
    public String nuevoServicioCliente(@PathVariable Integer id, Model model, RedirectAttributes flash) {
        if (ObjectUtils.isEmpty(clienteService.buscarPorId(id))) {
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR, Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        Cliente cliente = clienteService.buscarPorId(id);
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicio(new Servicio());
        servicioModel.setCliente(cliente);
        model.addAttribute(servicioService.enviarModelo(servicioModel, model));
        return Constantes.TEMPLATE_FORM_SERVICIOS;
    }

    @PostMapping(Constantes.GUARDAR)
    public String guardarServicio(@Valid Servicio servicio, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash){
        Cliente cliente = new Cliente();
        if( !ObjectUtils.isEmpty(servicio.getCliente().getId()) ){
            cliente = this.clienteService.buscarPorId(servicio.getCliente().getId());
        }
        ServicioModel servicioModel = new ServicioModel(servicio, cliente);
        model.addAttribute(servicioService.enviarModelo(servicioModel, model));

        if(   !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_SOLUCION))
                || !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_CLIENTE))
                || !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_SECTOR))) {
            return Constantes.TEMPLATE_FORM_SERVICIOS;
        }
        if(result.hasErrors()){
            return Constantes.TEMPLATE_FORM_SERVICIOS;
        }
        Sector sectorNuevo =  sectorService.buscarPorId(servicio.getSector().getId());
        this.servicioService.asignarSector(servicio, sectorNuevo);
        if (servicio.getId() != null){
            sectorService.guardar(sectorNuevo);
            servicioService.guardar(servicio);
            this.servicioService.enviarMail(servicio);
            status.setComplete();
            flash.addFlashAttribute(Constantes.SUCCESS, Constantes.MSJ_SERVICIO_ACTUALIZADO);
        }else{
            try{
                sectorService.guardar(sectorNuevo);
                servicioService.guardar(servicio);
            }catch(Exception e){
                return Constantes.REDIRECT_SERVICIOS;
            }
            status.setComplete();
            flash.addFlashAttribute(Constantes.SUCCESS_IMPRIMIR, Constantes.MSJ_SERVICIO_GUARDADO);
            flash.addFlashAttribute(Constantes.SERVICIO_ID, servicio.getId());
        }
        return Constantes.REDIRECT_SERVICIOS;
    }

    @GetMapping(Constantes.ELIMINAR_ID)
    public String eliminarServicio(@PathVariable Integer id, RedirectAttributes flash){
        if(servicioService.buscarPorId(id) == null){
            flash.addFlashAttribute(Constantes.ERROR, Constantes.MSJ_SERVICIO_NO_EXISTE);
            return Constantes.REDIRECT_SERVICIOS;
        }
        Servicio servicio = servicioService.buscarPorId(id);
        if(servicio.getSector() != null){
            Sector sector = sectorService.buscarPorId(servicio.getSector().getId());
            sector.setDisponible(true);
        }
        servicioService.eliminar(id);
        flash.addFlashAttribute(Constantes.SUCCESS, Constantes.MSJ_SERVICIO_ELIMINADO);
        return Constantes.REDIRECT_SERVICIOS;

    }  

    @GetMapping(Constantes.MONITOR)
    public String monitor(Model model){
        List<Servicio> serviciosPendientes = servicioService.buscarPorEstadoServicioMonitor(Estado.PENDIENTE);
        List<Servicio> serviciosEnProceso = servicioService.buscarPorEstadoServicioMonitor(Estado.EN_PROCESO);
        
        model.addAttribute(Constantes.TOTAL_PENDIENTES, serviciosPendientes.size());
        model.addAttribute(Constantes.TOTAL_EN_PROCESO, serviciosEnProceso.size());
        
        model.addAttribute(Constantes.SERVICIOS_PENDIENTES, serviciosPendientes);
        model.addAttribute(Constantes.SERVICIOS_EN_PROCESO, serviciosEnProceso);
        model.addAttribute(Constantes.TITULO, Constantes.TITULO_VISUALIZADOR_DE_SERVICIOS);
        return Constantes.TEMPLATE_MONITOR;
    }

    @PostMapping(Constantes.BUSCAR_ESTADO)
    public String buscarServicio(@RequestParam String param,@PathVariable String estado, Model model, RedirectAttributes flash){
        
        if(StringUtils.isEmpty(param)){
            return Constantes.REDIRECT_SERVICIOS;
        }
        List<Servicio> servicios = this.servicioService.buscarPorParamEstado(param, estado);
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        if(!servicios.isEmpty()){
            model.addAttribute(this.servicioService.enviarModelo(servicioModel,model));
            return Constantes.TEMPLATE_LISTA_SERVICIOS;
        } else {
            flash.addFlashAttribute(Constantes.WARNING, Constantes.MSJ_SERVICIO_NO_ENCONTRADO);
            return Constantes.REDIRECT_SERVICIOS;
        }
    } 

    @PostMapping(Constantes.BUSCAR_CLIENTES)
    public String buscarClientesEnServicioNuevo(@RequestParam String param, Model model, RedirectAttributes flash){
        if( StringUtils.isEmpty(param) ){
            return Constantes.REDIRECT_SERVICIOS_NUEVO;
        }
        List<Cliente> clientes = this.clienteService.buscarPorParametro(param);
        if(!clientes.isEmpty()){
            model.addAttribute(Constantes.CLIENTES, clientes);
            model.addAttribute(Constantes.ACTIVE, Constantes.SERVICIOS);
            this.nuevoServicio(model);
            return Constantes.TEMPLATE_FORM_SERVICIOS;
        } else {
            flash.addFlashAttribute(Constantes.WARNING, Constantes.MSJ_CLIENTE_NO_ENCONTRADO);
            
            return Constantes.REDIRECT_SERVICIOS_NUEVO;
        }
    }  
    @PostMapping(Constantes.BUSCAR_CLIENTES_ID)
    public String buscarClientesEnServicio(@RequestParam String param, @PathVariable Integer id, Model model, RedirectAttributes flash){
        if( StringUtils.isEmpty(param)){
            return Constantes.REDIRECT_SERVICIOS_EDITAR;
        }
        List<Cliente> clientes = this.clienteService.buscarPorParametro(param);
        if(!clientes.isEmpty()){
            model.addAttribute(Constantes.CLIENTES, clientes);
            model.addAttribute(Constantes.ACTIVE, Constantes.SERVICIOS);
            this.editarServicio(id, model, flash);
            return Constantes.TEMPLATE_FORM_SERVICIOS;
        } else {
            flash.addFlashAttribute(Constantes.WARNING, Constantes.MSJ_CLIENTE_NO_ENCONTRADO);
            
            return Constantes.REDIRECT_SERVICIOS_EDITAR;
        }
    }
}
