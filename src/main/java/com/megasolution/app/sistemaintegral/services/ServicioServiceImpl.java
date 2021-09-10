package com.megasolution.app.sistemaintegral.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.models.entities.Aviso;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Estado;
import com.megasolution.app.sistemaintegral.models.entities.Llamado;
import com.megasolution.app.sistemaintegral.models.entities.Mensaje;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.models.repositories.IServicioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;


@Service
public class ServicioServiceImpl implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepo;

    @Autowired
    private IAvisoService avisoService;

    @Autowired
    private IMensajeService mensajeService;

    @Autowired
    private ILlamadoService llamadoService;

    @Autowired
    private IMailService mailService;

    @Autowired
    private ISectorService sectorService;

    private final Logger log = LoggerFactory.getLogger(ServicioServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarTodos() {
        return servicioRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Servicio buscarPorId(Integer id) {
        return servicioRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer contarServicios() {
        return servicioRepo.contarServicios();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoServicio(String codigoEstado) {
        Integer nroId = 0;
        switch(codigoEstado) {
            case Estado.PENDIENTE:
                nroId = 1;
                break;
            case Estado.EN_PROCESO:
                nroId = 2;
                break;
            case Estado.TERMINADO:
                nroId = 3;
                break;
            case Estado.ENTREGADO:
                nroId = 4;
                break;
            case Estado.GUARDADO:
                nroId = 5;
                break;
        }
        return servicioRepo.findByEstadoServicio(nroId);
    }

    @Override
    @Transactional
    public void guardar(Servicio servicio) {
        log.info("servicio guardado!");
        servicioRepo.save(servicio);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        log.info("servicio eliminado!");
        servicioRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorServicioConClienteId(Integer id) {
      return servicioRepo.findByServicioWithClienteId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoPorCliente(Integer estadoId, Integer clienteId) {
        return servicioRepo.findByServicioWithEstadoIdWithClienteId(estadoId, clienteId);
    }

    public void recuperarEstadoTerminado(Servicio servicio){
        if(servicio.getEstado().getCodigo().equals(Estado.ENTREGADO)){
            servicio.setFechaTerminado(new Date());
         }else{
             servicio.setFechaTerminado(null);
         }
    }

    @Override
    public Servicio buscarServicioPorSector(Integer id) {
        return servicioRepo.buscarServicioPorSector(id);
    }

    @Override
    public Integer buscarServiciosDeHoy(String fechaHoy) {
        return servicioRepo.buscarServiciosDeHoy(fechaHoy);
    }

    public long promedioServicios(){
        List<Servicio> servicios = servicioRepo.findByEstadoServicio(3);
        Date fechaActual = new Date();
        long tiempoTotal = 0;
        int nroServicios7Dias = 0;
        for (Servicio servicio : servicios) {
            
            if(fechaActual.getTime() - servicio.getFechaIngreso().getTime() <= 604800000){ // 7dias = 604800000 ms - 1dia = 86400000 - 1hr= 3600000
                nroServicios7Dias++;
                float tiempo = (servicio.getFechaTerminado().getTime() - servicio.getFechaIngreso().getTime());
                float tiempoEnHoras = (float) Math.ceil(tiempo / 3600000);
                tiempoTotal += tiempoEnHoras;
            }           
        }
        if(tiempoTotal != 0){
            return tiempoTotal / nroServicios7Dias;
        }else{
            return tiempoTotal;
        }

    }

    @Override
    public List<Servicio> buscarPorEstadoServicioMonitor(Integer id) {
        return servicioRepo.findByEstadoServicioMonitor(id);
    }

    @Override
    public List<Servicio> buscarPorParametro(String param, String estado) {

        param = param.toLowerCase();

        List<Servicio> servicios = this.servicioRepo.findByParam(param, estado);
        return servicios;
    }

    @Override
    public List<Servicio> buscar50Ultimos() {
       return this.servicioRepo.findLast50();
    }

    @Override
    public Model validarForm(Servicio servicio, Model model){
        model = validarSolucion(servicio, model);
        model = validarSector(servicio, model);
        model = validarCliente(servicio, model);
        return model;
    }

 
    private Model validarSolucion(Servicio servicio, Model model) {

        if(servicio.getSolucion().isEmpty() &&
            (servicio.getEstado().getCodigo().equals(Estado.TERMINADO) || servicio.getEstado().getCodigo().equals(Estado.ENTREGADO))) {
                model.addAttribute("errorSolucion", "Debe ingresar una solución antes de guardar el servicio!");
                model.addAttribute("alertDangerSolucion", " form-control alert-danger");
        } 
        return model;
        
    }

    private Model validarCliente(Servicio servicio, Model model){

        if(servicio.getCliente().getId() == null){
            model.addAttribute("errorCliente", "Debe seleccionar un cliente antes de guardar!");
            model.addAttribute("alertDangerCliente", " form-control alert-danger");
        }
        return model;

    }

    private Model validarSector(Servicio servicio, Model model){
        if(servicio.getSector().getId() == null){
            model.addAttribute("errorSector", "Debe seleccionar un sector antes de guardar!");
            model.addAttribute("alertDangerSector", " form-control alert-danger");
        }
        return model;
    }

    @Override
    public Servicio asignarSector(Servicio servicio, Sector sector){
        if(servicio.getEstado().getCodigo().equals(Estado.ENTREGADO) || servicio.getEstado().getCodigo().equals(Estado.GUARDADO)){ 
            sector.setDisponible(true);
            servicio.setSector(null); 
        }else{
            sector.setDisponible(false);
        }
        sectorService.guardar(sector);
        return servicio;
    }



    @Override
    public void crearAviso(Servicio servicio){
        Aviso aviso = new Aviso();
        Aviso avisoBuscado = new Aviso();
        if(servicio.getEstado().getCodigo().equals(Estado.TERMINADO)){
           
            try {
                // Envío informacion del cliente al html del correo determinado
                String contenido = this.mailService.avisoServicioTerminado(servicio.getCliente(), servicio);
                
                this.mailService.enviarMail(servicio.getCliente().getEmail(), contenido);
            } catch (MessagingException | BadElementException | IOException e) {
                ((Throwable) e).printStackTrace();
            }
            Llamado llamado = llamadoService.buscarPorId(1);
            Mensaje mensaje = mensajeService.buscarPorId(1);
            avisoBuscado = avisoService.buscarAvisoPorServicioId(servicio.getId());
            if (avisoBuscado == null){
                aviso = new Aviso(mensaje.getTipoMensaje(), mensaje, servicio, llamado);
                avisoService.guardar(aviso);
            }

        }else{
            aviso = avisoService.buscarAvisoPorServicioId(servicio.getId());
            if(aviso != null){
                avisoService.eliminar(aviso.getId());
                servicio.setAviso(null);
            }
        }
    }

    @Override
    public Model enviarModelo(Cliente cliente, List<Sector> sectores, List<Estado> estados, Sector sector, Servicio servicio, Model model ){

        model.addAttribute("active", "servicio");
        if(cliente.getId() != null){
            model.addAttribute("cliente", cliente.getDniCuit() + " - " + cliente.getRazonSocial());
            model.addAttribute("telefono", cliente.getTelefono());
        }
        model.addAttribute("estados", estados);
        model.addAttribute("sectores", sectores);
        model.addAttribute("sector", sector.getNombre());
        
        recuperarEstadoTerminado(servicio);

        return model;
    }

    @Override
    public List<Servicio> buscarPorParamEstado(String param, String estado){
        estado = estado.toUpperCase();
        List<Servicio> servicios = null;
        switch (estado){
            case "TODOS":
                servicios = this.buscarPorParametro(param, "");
                break;
            case Estado.PENDIENTE:
                servicios = this.buscarPorParametro(param, Estado.PENDIENTE);
                break;
            case Estado.EN_PROCESO:
                servicios = this.buscarPorParametro(param, Estado.EN_PROCESO); 
                break;
            case Estado.TERMINADO:
                servicios = this.buscarPorParametro(param, Estado.TERMINADO);
                break;
            case Estado.ENTREGADO:
                servicios = this.buscarPorParametro(param, Estado.ENTREGADO);
                break;       
            case Estado.GUARDADO:
                servicios = this.buscarPorParametro(param, Estado.GUARDADO);
                break;       
        }

        return servicios;
    }
 
}
