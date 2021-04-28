package com.megasolution.app.sistemaintegral.email;

import java.io.IOException;

import javax.mail.MessagingException;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;

public interface IMailService {
    public void enviarMail(String destinatario, String contenido) throws MessagingException;

    public String avisoServicioTerminado(Cliente cliente, Servicio servicio ) throws MessagingException, BadElementException, IOException;
}