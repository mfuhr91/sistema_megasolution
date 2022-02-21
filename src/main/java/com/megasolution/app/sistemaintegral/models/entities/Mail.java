package com.megasolution.app.sistemaintegral.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import com.megasolution.app.sistemaintegral.utils.Estado;
import com.megasolution.app.sistemaintegral.utils.TipoMail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "mails")
@Getter
@Setter
public class Mail implements Serializable {

    private static final long serialVersionUID = 354664130521988245L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private TipoMail tipoMail;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @DateTimeFormat(pattern = "HH:mm dd/MM/yyyy")
    private LocalDateTime fecha;

    public Mail(){
        this.fecha = LocalDateTime.now();
    }

}
