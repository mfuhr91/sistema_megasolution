package com.megasolution.app.sistemaintegral.avisos.models.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "llamados")
@Data
@NoArgsConstructor
public class Llamado implements Serializable{

	private static final long serialVersionUID = -2167880175960518943L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_llamado")
    private String nombreLlamado;
    
    @NotNull
    private Integer horas;
    
}
