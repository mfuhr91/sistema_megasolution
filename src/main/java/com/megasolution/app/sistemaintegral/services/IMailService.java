package com.megasolution.app.sistemaintegral.services;

import java.io.IOException;

import javax.mail.MessagingException;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Mail;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;

public interface IMailService {
    
    public void enviarMail(String destinatario, String contenido) throws MessagingException;

    public String avisoServicioTerminado(Cliente cliente, Servicio servicio ) throws MessagingException, BadElementException, IOException;

    public void guardar(Mail mail);
}

