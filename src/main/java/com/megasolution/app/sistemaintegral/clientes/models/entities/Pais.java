package com.megasolution.app.sistemaintegral.clientes.models.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paises")
@Data
@NoArgsConstructor
public class Pais implements Serializable{

	private static final long serialVersionUID = 2624401450484056370L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    
}
