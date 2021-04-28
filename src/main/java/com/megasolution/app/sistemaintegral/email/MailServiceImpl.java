package com.megasolution.app.sistemaintegral.email;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements IMailService {
    
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    //Importante hacer la inyección de dependencia de JavaMailSender:
    @Autowired
    private JavaMailSender emailSender;

    private TemplateEngine templateEngine;

    private String asunto;

    private String remitente;

    @Autowired
    public MailServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


    @Override
    @Async
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
        
            emailSender.send(message);


        }catch(MailException m){
            System.out.println(m.getMessage());
            
        }

        logger.info("Email enviado a: {}, con asunto: {}", destinatario, asunto);
        

    }

    public String avisoServicioTerminado(Cliente cliente, Servicio servicio ) throws MessagingException, BadElementException, IOException{

         //TODO: MAIL

         this.remitente = "serviciotecnico@megasolution.com.ar";
         this.asunto = "¡Equipo terminado y listo para ser retirado! - Equipo de MegaSolution";

         Context context = new Context();

         context.setVariable("razonSocial", cliente.getRazonSocial());
         context.setVariable("equipo", servicio.getEquipo());
         
         return templateEngine.process("email/servicio-terminado", context);

         /*
         String contenido = "<h2>¡Hola ".concat(cliente.getRazonSocial()).concat("!\n\n</h2>")
         .concat("                           <p>Su equipo " + servicio.getEquipo() + " está listo para ser retirado, recuerde que nuestro horario de atención")
         .concat(" es de Lunes a Sábado 10 a 13 y de 15:30 a 20:00hs. ¡Te esperamos!\n\n</p>")
         .concat("<img src='/img/header_mega.jpg' />")
         .concat("¡Muchas gracias!");
         
         String asunto = "¡Equipo terminado y listo para ser retirado! - Equipo de MegaSolution";
         String destinatario = cliente.getEmail();

         this.enviarMail(destinatario, asunto, contenido);

         */

    }

}
