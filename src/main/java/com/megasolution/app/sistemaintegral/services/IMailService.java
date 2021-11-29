package com.megasolution.app.sistemaintegral.services;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.lowagie.text.BadElementException;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Mail;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.utils.TipoMail;

public interface IMailService {
    
    public void enviarMail(String destinatario, String contenido) throws MessagingException;

    public String crearContenidoServicioTerminado(Cliente cliente, Servicio servicio ) throws MessagingException, BadElementException, IOException;

    public void enviarMailServicioTerminado(Servicio servicio) throws BadElementException, MessagingException, IOException;

    public void guardar(Mail mail);

    public void eliminar(Mail mail);

    public void enviarMailsNoEnviados();

    public void crearMail(Cliente cliente, Servicio servicio, TipoMail tipoMail);

}

