package com.megasolution.app.sistemaintegral.services;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.models.ServicioModel;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Mail;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.models.repositories.IServicioRepository;
import com.megasolution.app.sistemaintegral.utils.Constantes;
import com.megasolution.app.sistemaintegral.utils.Estado;
import com.megasolution.app.sistemaintegral.utils.TipoMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ServicioService {

    private final Logger LOG = LoggerFactory.getLogger(ServicioService.class);

    Sector sector = null;

    private IServicioRepository servicioRepo;

    private ClienteService clienteService;

    private MailService mailService;

    private SectorService sectorService;

    public ServicioService(IServicioRepository servicioRepo, ClienteService clienteService, MailService mailService, SectorService sectorService) {
        this.servicioRepo = servicioRepo;
        this.clienteService = clienteService;
        this.mailService = mailService;
        this.sectorService = sectorService;
    }

    @Transactional(readOnly = true)
    public List<Servicio> buscarTodos() {
        return servicioRepo.findByOrderByFechaIngresoDesc();
    }

    @Transactional(readOnly = true)
    public Servicio buscarPorId(Integer id) {
        return servicioRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Integer contarServicios() {
        return servicioRepo.countAllBy();
    }

    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoServicio(Estado estado) {
        if( estado.equals(Estado.PENDIENTE) || estado.equals(Estado.EN_PROCESO)) {
            return servicioRepo.findByEstadoOrderByFechaIngresoAsc(estado);
        }
        return servicioRepo.findByEstadoOrderByFechaIngresoDesc(estado);
    }

    @Transactional
    public void guardar(Servicio servicio) {
        servicioRepo.save(servicio);

        String sectorNombre = "NA";
        if ( !ObjectUtils.isEmpty(servicio.getSector()) ) {
            sectorNombre = servicio.getSector().getNombre();
            if (!ObjectUtils.isEmpty(servicio.getSector().getId())) {
                Sector sector = sectorService.buscarPorId(servicio.getSector().getId());
                sectorNombre = sector.getNombre();
            }
        }
        LOG.info("{} guardado! - Sector: {}", servicio, sectorNombre);
    }

    @Transactional
    public void eliminar(Integer id) {
        Servicio servicio = buscarPorId(id);
        List<Mail> mails = mailService.findByServicio(servicio);

        mails.forEach( mail -> {
            mailService.eliminar(mail);
        } );

        servicioRepo.deleteById(id);
        LOG.info("{} eliminado!", servicio);
    }

    @Transactional(readOnly = true)
    public List<Servicio> buscarPorServicioConClienteId(Integer id) {
      return servicioRepo.findByServicioWithClienteId(id);
    }

    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoPorCliente(Estado estado, Cliente cliente) {
        if( estado.equals(Estado.PENDIENTE) || estado.equals(Estado.EN_PROCESO)) {
            return servicioRepo.findByEstadoAndClienteOrderByFechaIngresoAsc(estado, cliente);
        }
        return servicioRepo.findByEstadoAndClienteOrderByFechaIngresoDesc(estado, cliente);
    }

    @Transactional(readOnly = true)
    public Servicio buscarServicioPorSector(Integer id) {
        return servicioRepo.buscarServicioPorSector(id);
    }

    @Transactional(readOnly = true)
    public Integer buscarServiciosDeHoy(String fechaHoy) {
        return servicioRepo.buscarServiciosDeHoy(fechaHoy);
    }

    @Transactional(readOnly = true)
    public Double promedioServicios() {
        List<Servicio> servicios = this.buscarPorEstadoServicio(Estado.TERMINADO);
        LocalDateTime fechaActual = LocalDateTime.now();

        double tiempoTotal = 0.0;
        int nroServicios7Dias = 0;

        for (Servicio servicio : servicios) {
            long dif = ChronoUnit.MILLIS.between(servicio.getFechaIngreso(), fechaActual);
            if( dif <= Constantes.SIETE_DIAS_EN_MS){ // 7dias = 604800000 ms - 1dia = 86400000 - 1hr= 3600000
                nroServicios7Dias++;
                float tiempo = ChronoUnit.MILLIS.between(servicio.getFechaIngreso(), servicio.getFechaTerminado());
                float tiempoEnHoras = (float) Math.ceil(tiempo / Constantes.UNA_HORA_EN_MS);
                tiempoTotal += tiempoEnHoras / 10; // 10 horas por dia
            }
        }

        if(tiempoTotal != 0){
            tiempoTotal = Math.ceil((tiempoTotal / nroServicios7Dias) * 100 ) / 100;

            return tiempoTotal;
        }

        return tiempoTotal;
    }

    @Transactional(readOnly = true)
    public List<Servicio> buscarPorEstadoServicioMonitor(Estado estado) {
        return servicioRepo.findByEstadoServicioMonitor(estado.getValor());
    }

    @Transactional(readOnly = true)
    public List<Servicio> buscarPorParametro(String param, String estado) {
        param = param.toLowerCase();
        String[] params = param.split(" ");
        Set<Servicio> servicios = new LinkedHashSet<>();
        Arrays.stream(params).forEach( prm -> servicios.addAll(this.servicioRepo.findByParam(prm, estado)) );
        Set<Servicio> resultados = new LinkedHashSet<>();

        for (Servicio servicio: servicios) {
            if ( servicio.getCliente().getRazonSocial().toLowerCase().contains(param) ) {
                resultados.add(servicio);
            }
        }
        resultados.addAll(servicios);
        return List.copyOf(resultados);
    }

    @Transactional(readOnly = true)
    public List<Servicio> buscar50Ultimos() {
       return this.servicioRepo.findLast50();
    }

    public void asignarSector(Servicio servicio){
        // NO SE CONSULTA POR ID, XQ POR ALGUNA RAZON SPRING CAMBIA EL ID DEL SECTOR ALMACENADO EN CACHE POR EL SELECCIONADO
        Sector sectorAnterior = null;
        if (this.sector != null) {
            sectorAnterior = sectorService.buscarPorNombre(this.sector.getNombre());
        }
        Sector sectorNuevo = sectorService.buscarPorId(servicio.getSector().getId());

        if ( !servicio.getEstado().equals(Estado.ENTREGADO) && !servicio.getEstado().equals(Estado.GUARDADO) ) {
            sectorNuevo.setServicio(servicio);
            guardar(servicio);
            sectorService.guardar(sectorNuevo);
        }

        if ( sectorAnterior != null ) {
            if ( ( servicio.getEstado().equals(Estado.ENTREGADO) || servicio.getEstado().equals(Estado.GUARDADO) ) &&
                    servicio.getId().equals(sectorNuevo.getServicio().getId()) ) {
                sectorAnterior.setServicio(null);
                LOG.info("sector {} liberado!", sectorAnterior.getNombre());
                sectorService.guardar(sectorAnterior);
            } else {
                if ( !sectorAnterior.getNombre().equalsIgnoreCase(sectorNuevo.getNombre()) ) {

                    sectorAnterior.setServicio(null);
                    LOG.info("sector {} liberado!", sectorAnterior.getNombre());
                    sectorService.guardar(sectorAnterior);
                }
            }
        }

    }

    public void almacenarSectorAnterior(Sector sector) {
        this.sector = sector;
    }

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

    public Model validarForm(Servicio servicio, Model model){
        if( !ObjectUtils.isEmpty(servicio) ){
            model.addAttribute(validarSolucion(servicio, model));
            model.addAttribute(validarSector(servicio, model));

            if( !ObjectUtils.isEmpty(servicio.getCliente()) && ObjectUtils.isEmpty(servicio.getCliente().getId())){
                model.addAttribute(Constantes.ERROR_CLIENTE, Constantes.MSJ_INGRESAR_CLIENTE);
                model.addAttribute(Constantes.ALERT_DANGER_CLIENTE, Constantes.ESPACIO_ALERT_DANGER);
            }

        }
        return model;
    }

    public Model getForm(Servicio servicio, Model model){
        if( !ObjectUtils.isEmpty(servicio) ){
            if ( !ObjectUtils.isEmpty(servicio.getSector()) ) {
                model.addAttribute(Constantes.SECTOR, servicio.getSector().getNombre());
            }

            model.addAttribute(getCliente(servicio, model));

        }
        return model;
    }

    private Model validarSolucion(Servicio servicio, Model model) {
        if( servicio.getSolucion() != null && servicio.getSolucion().isEmpty() &&
                (servicio.getEstado().equals(Estado.TERMINADO) || servicio.getEstado().equals(Estado.ENTREGADO))) {
            model.addAttribute(Constantes.ERROR_SOLUCION, Constantes.MSJ_INGRESAR_SOLUCION);
            model.addAttribute(Constantes.ALERT_DANGER_SOLUCION, Constantes.ESPACIO_ALERT_DANGER);
        }

        return model;
    }

    private Model validarSector(Servicio servicio, Model model){
        // SECTOR NO ES NULL PERO EL ID SI - FORMULARIO EN BLANCO
        if( !ObjectUtils.isEmpty(servicio.getSector()) && ObjectUtils.isEmpty(servicio.getSector().getId())){
            if ( !servicio.getEstado().equals(Estado.ENTREGADO) && !servicio.getEstado().equals(Estado.GUARDADO) ) {
                model.addAttribute(Constantes.ERROR_SECTOR, Constantes.MSJ_INGRESAR_SECTOR);
                model.addAttribute(Constantes.ALERT_DANGER_SECTOR, Constantes.ESPACIO_ALERT_DANGER);
            }
        } else if ( !ObjectUtils.isEmpty(servicio.getSector()) && !ObjectUtils.isEmpty(servicio.getSector().getId())) {
            Sector sector = sectorService.buscarPorId(servicio.getSector().getId());

            if ( (sector.getServicio() != null && !sector.getServicio().getId().equals(servicio.getId()) ) &&
                    !servicio.getEstado().equals(Estado.ENTREGADO) && !servicio.getEstado().equals(Estado.GUARDADO) ) {
                model.addAttribute(Constantes.ERROR_SECTOR, Constantes.MSJ_SECTOR_NO_DISPONIBLE);
                model.addAttribute(Constantes.ALERT_DANGER_SECTOR, Constantes.ESPACIO_ALERT_DANGER);
            } else {
                model.addAttribute(Constantes.SECTOR, sector.getNombre());
            }

        }

        return model;
    }

    private Model validarEstado(Servicio servicio, Model model) {
        // ESTADO NO ES NULL Y ES ENTREGADO O TERMINADO
        if( !ObjectUtils.isEmpty(servicio.getEstado()) &&
                servicio.getEstado().equals(Estado.TERMINADO) ){
            servicio.setFechaTerminado(LocalDateTime.now());
        } else if ( !ObjectUtils.isEmpty(servicio.getEstado()) &&
                servicio.getEstado().equals(Estado.ENTREGADO) ) {
            servicio.setFechaEntregado(LocalDateTime.now());
        } else{
            servicio.setFechaTerminado(null);
            servicio.setFechaEntregado(null);
        }
        model.addAttribute(Constantes.SERVICIO, servicio);
        return model;
    }

    private Model getCliente(Servicio servicio, Model model){
        // CLIENTE NO ES NULL Y EL ID NO ES NULL - FORMULARIO EN BLANCO
        if ( !ObjectUtils.isEmpty(servicio.getCliente()) &&
                !ObjectUtils.isEmpty(servicio.getCliente().getId()) ){
            StringBuilder cliente = new StringBuilder();
            cliente.append(servicio.getCliente().getDniCuit().toString());
            cliente.append(" - ");
            cliente.append(servicio.getCliente().getRazonSocial());
            model.addAttribute(Constantes.ID, servicio.getCliente().getId());
            model.addAttribute(Constantes.CLIENTE, cliente);
            model.addAttribute(Constantes.TELEFONO, servicio.getCliente().getTelefono());
        }

        return model;
    }

    public Model getModelList(ServicioModel servicioModel, Model model ){
        // PILL ACTIVO EN MENU = SERVICIOS
        model.addAttribute(Constantes.ACTIVE, Constantes.SERVICIOS);
        model.addAttribute(Constantes.TITULO, Constantes.TITULO_SERVICIOS);
        model.addAttribute(Constantes.SERVICIOS, servicioModel.getServicios());
        model.addAttribute(Constantes.PILL_ACTIVO, Constantes.TODOS);

        return model;
    }

    public Model getModel(ServicioModel servicioModel, Model model ){

        Servicio servicio = servicioModel.getServicio();
        servicio.setCliente(servicioModel.getCliente());
        Model modeloResultado = getForm(servicio, model);
        model.addAttribute(modeloResultado);

        if ( ObjectUtils.isEmpty(servicio.getId())) {
            // SERVICIO NUEVO
            List<Estado> estados = Arrays.stream(Estado.values())
                    .filter(estado -> estado.equals(Estado.PENDIENTE) || estado.equals(Estado.EN_PROCESO))
                    .collect(Collectors.toList());
            if ( ObjectUtils.isEmpty(servicio.getBateria() ) ) {
                servicio.setCargador(true);
                servicio.setBateria(true);
            }
            servicio.setFechaIngreso(LocalDateTime.now());
            servicioModel.setEstados(estados);
            model.addAttribute(Constantes.SERVICIO, servicio);
            model.addAttribute(Constantes.TITULO, Constantes.TITULO_AGREGAR_SERVICIO);
        } else {
            // SERVICIO A EDITAR
            servicioModel.setEstados(Estado.getEstadosServicios());
            model.addAttribute(Constantes.SERVICIO, servicio);
            model.addAttribute(Constantes.SERVICIO_ID, servicio.getId());
            model.addAttribute(Constantes.TITULO, Constantes.TITULO_EDITAR_SERVICIO);

        }
        LocalDateTime hoy = LocalDateTime.now();
        hoy = hoy.truncatedTo(ChronoUnit.MINUTES);
        model.addAttribute("hoy", hoy);
        model.addAttribute(Constantes.ESTADOS, servicioModel.getEstados());
        model.addAttribute(Constantes.SECTORES, sectorService.buscarDisponibles());

        return model;
    }

    public List<Servicio> buscarPorParamEstado(String param, String estado){
        estado = estado.toUpperCase();
        List<Servicio> servicios;
        if(estado.equals(Constantes.TODOS.toUpperCase())){
            servicios = this.buscarPorParametro(param, "");
        } else {
            servicios = this.buscarPorParametro(param, estado);
        }
        return servicios;
    }

    public Model listarSegunClienteEstado(Integer id, Estado estado, Model model){
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute(listarSegunEstado(cliente,estado,model));
        return model;
    }

    public Model listarSegunEstado(Cliente cliente, Estado estado, Model model) {
        String pill_activo = Constantes.TODOS;
        StringBuilder logMsj = new StringBuilder("servicios");
        List<Servicio> servicios;
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

            logMsj.append(" con estado: ");
            logMsj.append(estado.getNombre());

            if ( ObjectUtils.isEmpty(cliente) ){
                servicios = this.buscarPorEstadoServicio(estado);
            } else {
                servicios = this.buscarPorEstadoPorCliente(estado, cliente);
                logMsj.append(" - del cliente: ");
                logMsj.append(cliente.getRazonSocial());
                model.addAttribute(Constantes.ID, cliente.getId());
            }
        } else {
            servicios = this.buscarPorServicioConClienteId(cliente.getId());
        }

        ServicioModel servicioModel = new ServicioModel();
        servicioModel.setServicios(servicios);
        logMsj.append(" - listados");
        LOG.info(logMsj.toString());

        Model resModel = this.getModelList(servicioModel, model);
        model.addAttribute(resModel);
        model.addAttribute(Constantes.PILL_ACTIVO, pill_activo);
        return model;
    }
}
