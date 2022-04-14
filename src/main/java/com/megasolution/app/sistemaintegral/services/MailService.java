package com.megasolution.app.sistemaintegral.services;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Mail;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.models.repositories.IMailRepository;
import com.megasolution.app.sistemaintegral.utils.TipoMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class MailService {

    private final Logger LOG = LoggerFactory.getLogger(MailService.class);

    private IMailRepository mailRepo;

    //Importante hacer la inyección de dependencia de JavaMailSender:
    private JavaMailSender emailSender;

    private TemplateEngine templateEngine;

    private String asunto;

    @Value("${spring.mail.username}")
    private String remitente;

    private Mail mail;

    public MailService(IMailRepository mailRepo, JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.mailRepo = mailRepo;
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

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
        LOG.info("enviando mail del cliente: {} , del tipo: {}", mail.getCliente().getRazonSocial(), mail.getTipoMail().getTipo());
        enviarMail(cliente.getEmail(), contenido);
    }

    public String crearContenidoValoracion(Cliente cliente) {
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
        LOG.info("enviando mail del cliente: {} , del tipo: {}", mail.getCliente().getRazonSocial(), mail.getTipoMail().getTipo());
        enviarMail(cliente.getEmail(), contenido);
    }


    private void crearMail( Cliente cliente, TipoMail tipoMail ){
        this.mail.setTipoMail(tipoMail);
        this.mail.setCliente(cliente);
    }

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

    public void guardar(Mail mail) {
        LOG.info("guardando mail del cliente: {} , del tipo: {}", mail.getCliente().getRazonSocial(), mail.getTipoMail().getTipo());
        this.mailRepo.save(mail);
        LOG.info("mail guardado!");
    }

    public void eliminar(Mail mail) {
        LOG.info("eliminando mail del cliente: {} , del tipo: {}", mail.getCliente().getRazonSocial(), mail.getTipoMail().getTipo());
        this.mailRepo.delete(mail);
        LOG.info("mail eliminado!");
    }

    public List<Mail> findByServicio(Servicio servicio) {
        return this.mailRepo.findByServicio(servicio);
    }

    @Scheduled(fixedDelay = 900_000 )
    public void enviarMailsNoEnviados(){
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime horaApertura = LocalDateTime.MIN.plusHours(10)
                .withYear(hoy.getYear())
                .withMonth(hoy.getMonthValue())
                .withDayOfMonth(hoy.getDayOfMonth());
        LocalDateTime horaCierre = LocalDateTime.MIN.plusHours(20)
                .withYear(hoy.getYear())
                .withMonth(hoy.getMonthValue())
                .withDayOfMonth(hoy.getDayOfMonth());

        if ( hoy.isAfter(horaApertura) && hoy.isBefore(horaCierre) ) {
            List<Mail> mails = this.mailRepo.findAll();
            LOG.info("comprobando mails no enviados...");
            for (Mail mail : mails) {
                this.mail = mail;
                if ( mail.getTipoMail().equals(TipoMail.SERVICIO_TERMINADO) ) {
                    try {
                        this.enviarMailServicioTerminado(mail.getServicio());
                        break;
                    } catch (MessagingException | IOException e) {
                        LOG.error(e.getMessage());
                    }
                } else {
                    if( hoy.isAfter(mail.getFecha().plusDays(7)) ){
                        try {
                            this.enviarMailValoracion(mail.getCliente());
                            break;
                        } catch (MessagingException | IOException e) {
                            LOG.error(e.getMessage());
                        }
                    }
                }

            }
        }
    }
}
