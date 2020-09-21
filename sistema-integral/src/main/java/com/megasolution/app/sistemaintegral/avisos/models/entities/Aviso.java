package com.megasolution.app.sistemaintegral.avisos.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.megasolution.app.sistemaintegral.servicios.models.entities.Servicio;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avisos")
@Data
@NoArgsConstructor
public class Aviso implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mensaje_id")
    private Mensaje mensaje;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
    
    private Boolean leido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_alta")
    private Date fechaAlta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_leido")
    private Date fechaLeido;

    @ManyToOne // NO PUEDE SER UN LAZY FETCH POR QUE NO LEE LOS LLAMADOS LEIDOS
    @JoinColumn(name = "llamado_id")
    private Llamado llamado;

    public Aviso(String nombre, Mensaje mensaje, Servicio servicio, Llamado llamado) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.servicio = servicio;
        this.leido = false;
        this.fechaAlta = new Date();
        this.llamado = llamado;
    }

    
}
