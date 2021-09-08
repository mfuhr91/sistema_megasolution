package com.megasolution.app.sistemaintegral.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mails")
@Data
@NoArgsConstructor
public class Mail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String asunto;

    @Column(columnDefinition="text")
    private String texto;

    private String destinatario;

    private String equipo;

    private Estado estado;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "HH:mm dd/MM/yyyy")
    private Date fecha;

}
