package com.megasolution.app.sistemaintegral.models.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "estados")
public class Estado implements Serializable{

	private static final long serialVersionUID = -4095715665479081208L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;
    
    private String nombre;

    @Transient
    public final static String PENDIENTE = "PENDIENTE";
    @Transient
    public final static String EN_PROCESO = "EN_PROCESO";
    @Transient
    public final static String TERMINADO = "TERMINADO";
    @Transient
    public final static String ENTREGADO = "ENTREGADO";
    @Transient
    public final static String GUARDADO = "GUARDADO";
    @Transient
    public final static String ENVIADO = "ENVIADO";
    @Transient
    public final static String NO_ENVIADO = "NO_ENVIADO";
    
}
