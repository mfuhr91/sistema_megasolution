package com.megasolution.app.sistemaintegral.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Mail;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.models.repositories.IMailRepository;

import com.megasolution.app.sistemaintegral.utils.Estado;
import com.megasolution.app.sistemaintegral.utils.TipoMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    private IMailRepository mailRepo;
    
    private final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    //Importante hacer la inyección de dependencia de JavaMailSender:
    @Autowired
    private JavaMailSender emailSender;

    private TemplateEngine templateEngine;

    private String asunto;

    private String remitente;

    private Mail mail;

    @Autowired
    public MailServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


    @Override
    public void enviarMail(String destinatario, String contenido) throws MessagingException {
        
       
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            
            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject(this.asunto);
            helper.setText(contenido,true);

            helper.addInline("imagen", new ClassPathResource("static/img/header_mega.jpg"), "image/jpg");
            helper.addInline("pin", new ClassPathResource("static/img/pin.png"), "image/png");
            helper.addInline("whatsapp", new ClassPathResource("static/img/whatsapp.png"), "image/png");

            emailSender.send(message);
            this.eliminar(mail);
            LOG.info("Email enviado a: {}, con asunto: {}", destinatario, asunto);

        }catch(MailException m){
            LOG.error("No se pudo enviar el mail por alguna razón, se intentará enviar automáticamente en unos minutos...");
            LOG.error(m.getMessage());
        }



    }

    public String crearContenidoServicioTerminado( Cliente cliente, Servicio servicio ) throws MessagingException, BadElementException, IOException {

        this.remitente = "serviciotecnico@megasolution.com.ar";
        this.asunto = "¡Equipo terminado y listo para ser retirado! - Equipo de MegaSolution";

        Context context = new Context();

        context.setVariable("razonSocial", cliente.getRazonSocial());
        context.setVariable("equipo", servicio.getEquipo());

        this.crearMail(cliente, servicio, TipoMail.SERVICIO_TERMINADO);

        return templateEngine.process("email/servicio-terminado", context);

    }

    @Async
    public void enviarMailServicioTerminado(Servicio servicio) throws BadElementException, MessagingException, IOException{
        Cliente cliente = servicio.getCliente();

        // Envío informacion del cliente al html del correo determinado
        String contenido = crearContenidoServicioTerminado(cliente, servicio);

        enviarMail(cliente.getEmail(), contenido);
    }

    public String crearContenidoValoracion(Cliente cliente) {
        this.remitente = "serviciotecnico@megasolution.com.ar";
        this.asunto = "¡Valoramos tu opinión! - Equipo de MegaSolution";

        Context context = new Context();

        context.setVariable("razonSocial", cliente.getRazonSocial());

        this.crearMail(cliente,null, TipoMail.VALORACION);
        return templateEngine.process("email/opinion", context);
    }

    @Async
    public void enviarMailValoracion(Cliente cliente) throws BadElementException, MessagingException, IOException{

        // Envío informacion del cliente al html del correo determinado
        String contenido = crearContenidoValoracion(cliente);

        enviarMail(cliente.getEmail(), contenido);
    }


    private void crearMail( Cliente cliente, TipoMail tipoMail ){
        this.mail.setTipoMail(tipoMail);
        this.mail.setCliente(cliente);
        this.mail.setEstado(Estado.NO_ENVIADO);
    }

    @Override
    public void crearMail(Cliente cliente, Servicio servicio, TipoMail tipoMail ){
        List<Mail> list;
        if( tipoMail.equals(TipoMail.SERVICIO_TERMINADO)) {
            list = this.mailRepo.findByTipoMailAndClienteAndServicio(tipoMail, cliente, servicio);
        } else {
            list = this.mailRepo.findByTipoMailAndCliente(tipoMail, cliente);
        }
        if( list.isEmpty() ){
            this.mail = new Mail();
        } else {
            this.mail = list.get(0);
        }
        if(ObjectUtils.isEmpty(servicio)){
            this.crearMail(cliente, tipoMail);
        } else {
            this.crearMail(cliente, tipoMail);
            this.mail.setServicio(servicio);
        }
        this.guardar(this.mail);
    }
    @Override
    public void guardar(Mail mail) {
        this.mailRepo.save(mail);
    }

    @Override
    public void eliminar(Mail mail) {
        this.mailRepo.delete(mail);
    }
    @Override
    @Scheduled(fixedDelay = 1_800_000)
    public void enviarMailsNoEnviados(){

        List<Mail> mails = this.mailRepo.findAllByEstado(Estado.NO_ENVIADO);
        LOG.info("comprobando mails no enviados...");
        LocalDateTime hoy = LocalDateTime.now();
        mails.forEach(mail -> {
            this.mail = mail;
            if ( mail.getTipoMail().equals(TipoMail.SERVICIO_TERMINADO) ) {
                try {
                    this.enviarMailServicioTerminado(mail.getServicio());
                } catch (MessagingException | IOException e) {
                    LOG.error(e.getMessage());
                }
            } else {
                if( hoy.isAfter(mail.getFecha().plusDays(7)) ){
                    try {
                        this.enviarMailValoracion(mail.getCliente());
                    } catch (MessagingException | IOException e) {
                        LOG.error(e.getMessage());
                    }
                }
            }
        });

    }



}
