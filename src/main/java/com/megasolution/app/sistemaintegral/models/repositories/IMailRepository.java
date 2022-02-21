package com.megasolution.app.sistemaintegral.models.repositories;

import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.utils.Estado;
import com.megasolution.app.sistemaintegral.models.entities.Mail;

import com.megasolution.app.sistemaintegral.utils.TipoMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMailRepository extends JpaRepository<Mail, Long>{

    public List<Mail> findByTipoMailAndClienteAndServicio(TipoMail tipoMail, Cliente cliente, Servicio servicio );

    public List<Mail> findByTipoMailAndCliente(TipoMail tipoMail, Cliente cliente);
}
