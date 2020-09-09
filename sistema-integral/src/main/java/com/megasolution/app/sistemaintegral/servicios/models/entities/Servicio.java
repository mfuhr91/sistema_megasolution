package com.megasolution.app.sistemaintegral.servicios.models.entities;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "servicios")
public class Servicio implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String equipo;

    private Boolean cargador;
    
    private Boolean bateria;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @NotNull
    private Integer sector;

    @NotBlank
    @Column(name = "problema_reportado")
    private String problemaReportado;

    
    private String observaciones;


    private String solucion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "HH:mm dd/MM/yyyy")
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "HH:mm dd/MM/yyyy")
    @Column(name = "fecha_terminado")
    private Date fechaTerminado;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name= "cliente_id")
    private Cliente cliente;

}
