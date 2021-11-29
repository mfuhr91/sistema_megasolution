package com.megasolution.app.sistemaintegral.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "condiciones_iva")
@Getter
@Setter
@NoArgsConstructor
public class CondicionIva implements Serializable{
    
	private static final long serialVersionUID = 7146594361928878175L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private Double porcentaje;
}
