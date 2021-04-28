package com.megasolution.app.sistemaintegral.email;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
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

         this.remitente = "serviciotecnico@megasolution.com.ar";
         this.asunto = "¡Equipo terminado y listo para ser retirado! - Equipo de MegaSolution";

         Context context = new Context();

         context.setVariable("razonSocial", cliente.getRazonSocial());
         context.setVariable("equipo", servicio.getEquipo());
         
         return templateEngine.process("email/servicio-terminado", context);

    }

}
