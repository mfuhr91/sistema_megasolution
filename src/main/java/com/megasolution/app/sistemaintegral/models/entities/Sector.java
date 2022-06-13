package com.megasolution.app.sistemaintegral.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "sectores")
@Getter
@Setter
public class Sector implements Serializable{

	private static final long serialVersionUID = -2397916530373844760L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String nombre;

    @OneToOne(fetch = FetchType.LAZY)
    private Servicio servicio;

}
