package com.megasolution.app.sistemaintegral.services;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.models.respuestaJson.Localidad;
import com.megasolution.app.sistemaintegral.models.Provincias;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.models.repositories.IServicioRepository;
import com.megasolution.app.sistemaintegral.utils.Estado;
import com.megasolution.app.sistemaintegral.utils.TipoMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class ServicioServiceImpl implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepo;

    @Autowired
    private IMailService mailService;

    @Autowired
    private ISectorService sectorService;

    private final Logger LOG = LoggerFactory.getLogger(ServicioServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarTodos() {
        return servicioRepo.findByOrderByFechaIngresoDesc();
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
    public List<Servicio> buscarPorEstadoServicio(Estado estado) {
        if( estado.equals(Estado.PENDIENTE) || estado.equals(Estado.EN_PROCESO)) {
            return servicioRepo.findByEstadoOrderByFechaIngresoAsc(estado);
        }
        return servicioRepo.findByEstadoOrderByFechaIngresoDesc(estado);
    }

    @Override
    @Transactional
    public void guardar(Servicio servicio) {
        LOG.info("servicio guardado!");
        servicioRepo.save(servicio);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        LOG.info("servicio eliminado!");
        servicioRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorServicioConClienteId(Integer id) {
      return servicioRepo.findByServicioWithClienteId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoPorCliente(Estado estado, Integer clienteId) {
        return servicioRepo.findByServicioWithEstadoWithClienteId(estado, clienteId);
    }

    public void recuperarEstadoTerminado(Servicio servicio){
        if(servicio.getEstado().equals(Estado.ENTREGADO)){
            servicio.setFechaTerminado(LocalDateTime.now());
         }else{
             servicio.setFechaTerminado(null);
         }
    }

    @Override
    @Transactional(readOnly = true)
    public Servicio buscarServicioPorSector(Integer id) {
        return servicioRepo.buscarServicioPorSector(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer buscarServiciosDeHoy(String fechaHoy) {
        return servicioRepo.buscarServiciosDeHoy(fechaHoy);
    }

    @Transactional(readOnly = true)
    public long promedioServicios() {
        List<Servicio> servicios = this.buscarPorEstadoServicio(Estado.TERMINADO);
        LocalDateTime fechaActual = LocalDateTime.now();
        long tiempoTotal = 0;
        int nroServicios7Dias = 0;
        for (Servicio servicio : servicios) {
            long dif = ChronoUnit.MILLIS.between(servicio.getFechaIngreso(), fechaActual);
            LOG.info(String.valueOf(dif));
            if( dif <= 604_800_000 ){ // 7dias = 604800000 ms - 1dia = 86400000 - 1hr= 3600000
                nroServicios7Dias++;
                float tiempo = ChronoUnit.MILLIS.between(servicio.getFechaIngreso(), servicio.getFechaTerminado());
                float tiempoEnHoras = (float) Math.ceil(tiempo / 3_600_000);
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
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoServicioMonitor(Estado estado) {
        return servicioRepo.findByEstadoServicioMonitor(estado.getValor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> buscarPorParametro(String param, String estado) {

        param = param.toLowerCase();

        List<Servicio> servicios = this.servicioRepo.findByParam(param, estado);
        return servicios;
    }

    @Override
    @Transactional(readOnly = true)
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
            (servicio.getEstado().equals(Estado.TERMINADO) || servicio.getEstado().equals(Estado.ENTREGADO))) {
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
        if(servicio.getEstado().equals(Estado.ENTREGADO) || servicio.getEstado().equals(Estado.GUARDADO)){
            sector.setDisponible(true);
            servicio.setSector(null); 
        }else{
            sector.setDisponible(false);
        }
        sectorService.guardar(sector);
        return servicio;
    }

    @Override
    public void enviarMail(Servicio servicio){
        if (servicio.getEstado().equals(Estado.TERMINADO)) {
            try {
                this.mailService.enviarMailServicioTerminado(servicio);
            } catch (MailException | MessagingException | BadElementException | IOException e) {
                LOG.error(e.getMessage());
            }
        } else if ( servicio.getEstado().equals(Estado.ENTREGADO)){
            this.mailService.crearMail(servicio.getCliente(), servicio, TipoMail.VALORACION);
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
        if(estado.equals("TODOS")){
            servicios = this.buscarPorParametro(param, "");
        } else {
            servicios = this.buscarPorParametro(param, estado);
        }
        return servicios;
    }

}
