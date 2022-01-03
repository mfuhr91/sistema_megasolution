package com.megasolution.app.sistemaintegral.services;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.models.ClienteModel;
import com.megasolution.app.sistemaintegral.models.Paises;
import com.megasolution.app.sistemaintegral.models.Provincias;
import com.megasolution.app.sistemaintegral.models.ServicioModel;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.models.repositories.IServicioRepository;
import com.megasolution.app.sistemaintegral.utils.Constantes;
import com.megasolution.app.sistemaintegral.utils.Estado;
import com.megasolution.app.sistemaintegral.utils.TipoMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ServicioServiceImpl implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepo;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IMailService mailService;

    @Autowired
    private ISectorService sectorService;

    Sector sector = null;

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
    public List<Servicio> buscarPorEstadoPorCliente(Estado estado, Cliente cliente) {
        if( estado.equals(Estado.PENDIENTE) || estado.equals(Estado.EN_PROCESO)) {
            return servicioRepo.findByEstadoAndClienteOrderByFechaIngresoAsc(estado, cliente);
        }
        return servicioRepo.findByEstadoAndClienteOrderByFechaIngresoDesc(estado, cliente);
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
            if( dif <= Constantes.SIETE_DIAS_EN_MS){ // 7dias = 604800000 ms - 1dia = 86400000 - 1hr= 3600000
                nroServicios7Dias++;
                float tiempo = ChronoUnit.MILLIS.between(servicio.getFechaIngreso(), servicio.getFechaTerminado());
                float tiempoEnHoras = (float) Math.ceil(tiempo / Constantes.UNA_HORA_EN_MS);
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
        String[] params = param.split(" ");
        List<Servicio> servicios = new ArrayList<>();
        Arrays.stream(params).forEach( prm -> servicios.addAll(this.servicioRepo.findByParam(prm, estado)) );
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

        if( !ObjectUtils.isEmpty(servicio.getSolucion()) && servicio.getSolucion().isEmpty() &&
            (servicio.getEstado().equals(Estado.TERMINADO) || servicio.getEstado().equals(Estado.ENTREGADO))) {
                model.addAttribute("errorSolucion", "Debe ingresar una solución antes de guardar el servicio!");
                model.addAttribute("alertDangerSolucion", " form-control alert-danger");
        } 
        return model;
        
    }

    private Model validarCliente(Servicio servicio, Model model){

        if( !ObjectUtils.isEmpty(servicio.getCliente()) && servicio.getCliente().getId() == null){
            model.addAttribute("errorCliente", "Debe seleccionar un cliente antes de guardar!");
            model.addAttribute("alertDangerCliente", " form-control alert-danger");
        }
        return model;

    }

    private Model validarSector(Servicio servicio, Model model){
        if( !ObjectUtils.isEmpty(servicio.getSector()) && servicio.getSector().getId() == null){
            model.addAttribute("errorSector", "Debe seleccionar un sector antes de guardar!");
            model.addAttribute("alertDangerSector", " form-control alert-danger");
        }
        return model;
    }

    @Override
    public void asignarSector(Servicio servicio, Sector sectorNuevo){
        Sector sectorAnterior = this.sector;
        liberarSectorAnterior(sectorAnterior, sectorNuevo);
        if(servicio.getEstado().equals(Estado.ENTREGADO) || servicio.getEstado().equals(Estado.GUARDADO)){
            servicio.setSector(null); 
        }
        sectorNuevo.setDisponible(false);
        sectorService.guardar(sectorNuevo);
    }

    private void liberarSectorAnterior(Sector sectorAnterior, Sector sectorNuevo){
        if ( !ObjectUtils.isEmpty(sectorAnterior) && !sectorAnterior.getNombre().equals(sectorNuevo.getNombre()) ) {
            Sector sector = this.sectorService.buscarPorNombre(sectorAnterior.getNombre());
            sector.setDisponible(true);
            LOG.info("sector liberado!");
            sectorService.guardar(sector);
        }
        this.sector = null;
    }

    @Override
    public void almacenarSectorAnterior(Sector sector) {
        this.sector = sector;
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
    public Model enviarModelo(ServicioModel servicioModel, Model model ){

        model.addAttribute(Constantes.ACTIVE, Constantes.SERVICIOS);
        if( servicioModel.getServicios() != null ) {
            model.addAttribute(Constantes.TITULO, Constantes.TITULO_SERVICIOS);
            model.addAttribute(Constantes.SERVICIOS, servicioModel.getServicios());
            model.addAttribute(Constantes.PILL_ACTIVO, Constantes.TODOS);
            return model;
        }

        model.addAttribute(validarForm(servicioModel.getServicio(), model));

        if(   !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_SOLUCION))
                || !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_CLIENTE))
                || !ObjectUtils.isEmpty(model.getAttribute(Constantes.ERROR_SECTOR))) {

            if( !ObjectUtils.isEmpty(servicioModel.getCliente().getId()) ){
                servicioModel.setCliente( clienteService.buscarPorId(servicioModel.getCliente().getId()) );
            }
            if( !ObjectUtils.isEmpty(servicioModel.getServicio().getSector().getId()) ) {
                servicioModel.getServicio().setSector( sectorService.buscarPorId(servicioModel.getServicio().getSector().getId()) );
            }
        }


        if ( ObjectUtils.isEmpty(servicioModel.getServicio().getId())) {
            List<Estado> estados = Arrays.stream(Estado.values())
                    .filter(estado -> estado.equals(Estado.PENDIENTE) || estado.equals(Estado.EN_PROCESO))
                    .collect(Collectors.toList());
            servicioModel.getServicio().setCargador(true);
            servicioModel.getServicio().setBateria(true);
            servicioModel.getServicio().setFechaIngreso(LocalDateTime.now());
            servicioModel.setEstados(estados);
            /*servicioModel.setCliente(new Cliente());*/
            model.addAttribute(Constantes.SERVICIO, servicioModel.getServicio());
            model.addAttribute(Constantes.TITULO, Constantes.TITULO_AGREGAR_SERVICIO);
            if( !ObjectUtils.isEmpty(servicioModel.getCliente()) ){
                model.addAttribute(Constantes.ID, servicioModel.getCliente().getId());
                model.addAttribute(Constantes.CLIENTE, servicioModel.getCliente().getDniCuit() + " - " + servicioModel.getCliente().getRazonSocial());
                model.addAttribute(Constantes.TELEFONO, servicioModel.getCliente().getTelefono());
            }
        } else {
            servicioModel.setEstados(Estado.getEstadosServicios());
            model.addAttribute(Constantes.CLIENTE, servicioModel.getCliente().getDniCuit() + " - " + servicioModel.getCliente().getRazonSocial());
            model.addAttribute(Constantes.TELEFONO, servicioModel.getCliente().getTelefono());
            model.addAttribute(Constantes.SERVICIO,servicioModel.getServicio());
            model.addAttribute(Constantes.SERVICIO_ID, servicioModel.getServicio().getId());
            model.addAttribute(Constantes.TITULO, Constantes.TITULO_EDITAR_SERVICIO);

            if( !ObjectUtils.isEmpty(servicioModel.getServicio().getSector()) ) {
                model.addAttribute(Constantes.SECTOR,servicioModel.getServicio().getSector().getNombre());
            }
            recuperarEstadoTerminado(servicioModel.getServicio());
        }
        model.addAttribute(Constantes.ESTADOS, servicioModel.getEstados());
        model.addAttribute(Constantes.SECTORES, sectorService.buscarDisponibles());

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

    @Override
    public Model listarSegunClienteEstado(Integer id, Estado estado, Model model){
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute(listarSegunEstado(cliente,estado,model));
        return model;
    }


    @Override
    public Model listarSegunEstado(Cliente cliente, Estado estado, Model model) {
        String pill_activo = Constantes.TODOS;
        StringBuilder logMsj = new StringBuilder("servicios ");
        List<Servicio> servicios = null;
        if( !ObjectUtils.isEmpty( estado )) {
            switch (estado) {
                case PENDIENTE: pill_activo = Constantes.PENDIENTE;
                break;
                case EN_PROCESO: pill_activo = Constantes.EN_PROCESO;
                break;
                case TERMINADO: pill_activo = Constantes.TERMINADO;
                break;
                case ENTREGADO: pill_activo = Constantes.ENTREGADO;
                break;
                case GUARDADO: pill_activo = Constantes.GUARDADO;
            }

            logMsj.append(estado.getNombre());

            if ( ObjectUtils.isEmpty(cliente) ){
                servicios = this.buscarPorEstadoServicio(estado);
            } else {
                servicios = this.buscarPorEstadoPorCliente(estado, cliente);
                logMsj.append(" del cliente ");
                logMsj.append(cliente.getRazonSocial());
                model.addAttribute(Constantes.ID, cliente.getId());
            }
        } else {
            servicios = this.buscarPorServicioConClienteId(cliente.getId());
        }

        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        logMsj.append("listados");
        LOG.info(logMsj.toString());

        model.addAttribute(this.enviarModelo(servicioModel, model));
        model.addAttribute(Constantes.PILL_ACTIVO, pill_activo);
        return model;
    }

    /*
        Cliente cliente = clienteService.buscarPorId(id);
        List<Servicio> servicios = servicioService.buscarPorEstadoPorCliente(Estado.PENDIENTE, cliente.getId());
        StringBuilder msjLog = new StringBuilder("servicios pendientes del cliente:");
        msjLog.append(cliente.getRazonSocial());
        msjLog.append(" listados");
        LOG.info(msjLog.toString());

        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        servicioModel.setCliente(cliente);
        model.addAttribute(servicioService.enviarModelo(servicioModel, model));
        model.addAttribute(Constantes.ID, id);
        model.addAttribute(Constantes.PILL_ACTIVO, Constantes.PENDIENTE);*/

}
