package com.megasolution.app.sistemaintegral.controllers;

import com.megasolution.app.sistemaintegral.models.ServicioModel;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.services.ClienteService;
import com.megasolution.app.sistemaintegral.services.SectorService;
import com.megasolution.app.sistemaintegral.services.ServicioService;
import com.megasolution.app.sistemaintegral.utils.Constantes;
import com.megasolution.app.sistemaintegral.utils.Estado;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("servicios")
@SessionAttributes("servicio")
public class ServicioController {

    private ServicioService servicioService;

    private ClienteService clienteService;

    private SectorService sectorService;

    public ServicioController(ServicioService servicioService, ClienteService clienteService, SectorService sectorService) {
        this.servicioService = servicioService;
        this.clienteService = clienteService;
        this.sectorService = sectorService;
    }

    private final Logger LOG = LoggerFactory.getLogger(ServicioController.class);

    @GetMapping()
    public String listar50Ultimos(Model model){
        List<Servicio> servicios = servicioService.buscar50Ultimos(); 
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        LOG.info("ultimos 50 servicios listados");
        Model resModel = servicioService.getModelList(servicioModel, model);
        model.addAttribute(resModel);
        return "servicios/lista";
    }
    @GetMapping("todos")
    public String listarServicios(Model model){
        List<Servicio> servicios = servicioService.buscarTodos();
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        LOG.info("todos los servicios listados");
        Model resModel = servicioService.getModelList(servicioModel, model);
        model.addAttribute(resModel);
        return "servicios/lista";
    }

    @GetMapping(Constantes.PENDIENTE)
    public String listarPendientes(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.PENDIENTE, model));
        return "servicios/lista";
    }
    @GetMapping(Constantes.EN_PROCESO)
    public String listarEnProceso(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.EN_PROCESO, model));
        return "servicios/lista";
    }
    @GetMapping(Constantes.TERMINADO)
    public String listarTerminado(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.TERMINADO, model));
        return "servicios/lista";
    }
    @GetMapping(Constantes.ENTREGADO)
    public String listarEntregado(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.ENTREGADO, model));
        return "servicios/lista";
    }
    @GetMapping(Constantes.GUARDADO)
    public String listarGuardado(Model model){
        model.addAttribute(this.servicioService.listarSegunEstado(null,Estado.GUARDADO, model));
        return "servicios/lista";
    }
    @GetMapping("cliente/{id}")
    public String serviciosPorCliente(@PathVariable Integer id,Model model,RedirectAttributes flash) {
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,null, model));
        return "servicios/lista";
    }

    @GetMapping("pendiente/cliente/{id}")
    public String listarPendientesCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.PENDIENTE, model));
        return "servicios/lista";
    }
    @GetMapping("en-proceso/cliente/{id}")
    public String listarEnProcesoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.EN_PROCESO, model));
        return "servicios/lista";
    }
    @GetMapping( "terminado/cliente/{id}")
    public String listarTerminadoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.TERMINADO, model));
        return "servicios/lista";
    }

    @GetMapping("entregado/cliente/{id}")
    public String listarEntregadoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.ENTREGADO, model));
        return "servicios/lista";
    }

    @GetMapping("guardado/cliente/{id}")
    public String listarGuardadoCliente(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(clienteService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_CLIENTE_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_CLIENTE_NO_EXISTE);
            return Constantes.REDIRECT_CLIENTES;
        }
        model.addAttribute(this.servicioService.listarSegunClienteEstado(id,Estado.GUARDADO, model));
        return "servicios/lista";
    }

    @GetMapping("editar/{id}")
    public String editarServicio(@PathVariable Integer id, Model model, RedirectAttributes flash){
        if( ObjectUtils.isEmpty(servicioService.buscarPorId(id)) ){
            LOG.error(Constantes.MSJ_SERVICIO_NO_EXISTE);
            flash.addFlashAttribute(Constantes.ERROR,Constantes.MSJ_SERVICIO_NO_EXISTE);
            return Constantes.REDIRECT_SERVICIOS;
        }
        ServicioModel servicioModel = new ServicioModel();
        if(id > 0){
            Servicio servicio = servicioService.buscarPorId(id);
            LOG.info("editando {}", servicio);
            Sector sector = (Sector) Hibernate.unproxy(servicio.getSector());
            LOG.info("sector: {} - del servicio_id: {}", sector != null ? sector.getNombre() : "NA", servicio.getId());
            servicioModel.setServicio(servicio);
            servicioModel.setCliente(servicio.getCliente());
            this.servicioService.almacenarSectorAnterior(sector);

        }
        model.addAttribute(servicioService.getModel(servicioModel, model));
        return "servicios/form-servicio";
    }
    
    // MAPEA A LA CLASE ImprimirServicio.java PARA GENERAR EL PDF
    @GetMapping("imprimir/{id}")
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
        return "/servicios/form-servicio";
    }

    @GetMapping("nuevo")
    public String nuevoServicio(Model model){
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicio(new Servicio());
        LOG.info("creando nuevo servicio");
        model.addAttribute(servicioService.getModel(servicioModel,model));
        return "servicios/form-servicio";
    }

    @GetMapping("nuevo/cliente/{id}")
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
        model.addAttribute(servicioService.getModel(servicioModel, model));
        return "servicios/form-servicio";
    }

    @PostMapping("guardar")
    public String guardarServicio(@Valid Servicio servicio, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash){
        Cliente cliente = new Cliente();

        if( !ObjectUtils.isEmpty(servicio.getCliente().getId()) ){
            cliente = this.clienteService.buscarPorId(servicio.getCliente().getId());
        }

        ServicioModel servicioModel = new ServicioModel(servicio, cliente);
        model.addAttribute(servicioService.getModel(servicioModel, model));
        Model modeloResultado = servicioService.validarForm(servicio, model);
        model.addAttribute(modeloResultado);

        if(   !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_SOLUCION))
                || !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_CLIENTE))
                || !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_SECTOR))) {
            return "servicios/form-servicio";
        }

        if(result.hasErrors()){
            return "servicios/form-servicio";
        }

        this.servicioService.asignarSector(servicio);

        if ( !ObjectUtils.isEmpty(servicio.getId()) ){
            servicioService.guardar(servicio);
            this.servicioService.enviarMail(servicio);
            flash.addFlashAttribute(Constantes.SUCCESS, Constantes.MSJ_SERVICIO_ACTUALIZADO);

        }else{
            servicioService.guardar(servicio);
            flash.addFlashAttribute(Constantes.SUCCESS_IMPRIMIR, Constantes.MSJ_SERVICIO_GUARDADO);
            flash.addFlashAttribute(Constantes.SERVICIO_ID, servicio.getId());
        }

        status.setComplete();
        return Constantes.REDIRECT_SERVICIOS;
    }

    @GetMapping("eliminar/{id}")
    public String eliminarServicio(@PathVariable Integer id, RedirectAttributes flash){
        if(servicioService.buscarPorId(id) == null){
            flash.addFlashAttribute(Constantes.ERROR, Constantes.MSJ_SERVICIO_NO_EXISTE);
            return Constantes.REDIRECT_SERVICIOS;
        }
        Servicio servicio = servicioService.buscarPorId(id);

        if( !ObjectUtils.isEmpty(servicio.getSector())){
            Sector sector = sectorService.buscarPorId(servicio.getSector().getId());
            sector.setServicio(null);
            sectorService.guardar(sector);
            LOG.info("sector {} liberado!", sector.getNombre());

        }
        servicioService.eliminar(id);
        flash.addFlashAttribute(Constantes.SUCCESS, Constantes.MSJ_SERVICIO_ELIMINADO);
        return Constantes.REDIRECT_SERVICIOS;

    }  

    @GetMapping("monitor")
    public String monitor(Model model){
        List<Servicio> serviciosPendientes = servicioService.buscarPorEstadoServicioMonitor(Estado.PENDIENTE);
        List<Servicio> serviciosEnProceso = servicioService.buscarPorEstadoServicioMonitor(Estado.EN_PROCESO);
        
        model.addAttribute(Constantes.TOTAL_PENDIENTES, serviciosPendientes.size());
        model.addAttribute(Constantes.TOTAL_EN_PROCESO, serviciosEnProceso.size());
        
        model.addAttribute(Constantes.SERVICIOS_PENDIENTES, serviciosPendientes);
        model.addAttribute(Constantes.SERVICIOS_EN_PROCESO, serviciosEnProceso);
        model.addAttribute(Constantes.TITULO, Constantes.TITULO_VISUALIZADOR_DE_SERVICIOS);
        return "servicios/monitor";
    }

    @PostMapping("buscar/{estado}")
    public String buscarServicio(@RequestParam String param,@PathVariable String estado, Model model, RedirectAttributes flash){

        if(StringUtils.isEmpty(param)){
            return Constantes.REDIRECT_SERVICIOS;
        }
        param = param.toLowerCase().trim();
        List<Servicio> servicios = this.servicioService.buscarPorParamEstado(param, estado);
        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        servicioModel.setEstado(estado);
        if(!servicios.isEmpty()){

            model.addAttribute(this.servicioService.getModelList(servicioModel,model));
            return "servicios/lista";
        } else {
            flash.addFlashAttribute(Constantes.WARNING, Constantes.MSJ_SERVICIO_NO_ENCONTRADO);
            return Constantes.REDIRECT_SERVICIOS;
        }
    } 

    @PostMapping("/buscar-clientes")
    public String buscarClientesEnServicioNuevo(@RequestParam String param, Model model, RedirectAttributes flash){
        if( StringUtils.isEmpty(param) ){
            return Constantes.REDIRECT_SERVICIOS_NUEVO;
        }
        List<Cliente> clientes = this.clienteService.buscarPorParametro(param);
        if(!clientes.isEmpty()){
            model.addAttribute(Constantes.CLIENTES, clientes);
            model.addAttribute(Constantes.ACTIVE, Constantes.SERVICIOS);
            this.nuevoServicio(model);
            return "servicios/form-servicio";
        } else {
            flash.addFlashAttribute(Constantes.WARNING, Constantes.MSJ_CLIENTE_NO_ENCONTRADO);
            
            return Constantes.REDIRECT_SERVICIOS_NUEVO;
        }
    }  
    @PostMapping("buscar-clientes/{id}")
    public String buscarClientesEnServicio(@RequestParam String param, @PathVariable Integer id, Model model, RedirectAttributes flash){
        if( StringUtils.isEmpty(param)){
            return Constantes.REDIRECT_SERVICIOS_EDITAR;
        }
        List<Cliente> clientes = this.clienteService.buscarPorParametro(param);
        if(!clientes.isEmpty()){
            model.addAttribute(Constantes.CLIENTES, clientes);
            model.addAttribute(Constantes.ACTIVE, Constantes.SERVICIOS);
            this.editarServicio(id, model, flash);
            return "servicios/form-servicio";
        } else {
            flash.addFlashAttribute(Constantes.WARNING, Constantes.MSJ_CLIENTE_NO_ENCONTRADO);
            
            return Constantes.REDIRECT_SERVICIOS_EDITAR;
        }
    }
}
