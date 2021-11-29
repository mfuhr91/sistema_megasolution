package com.megasolution.app.sistemaintegral.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "llamados")
@Getter
@Setter
@NoArgsConstructor
public class Llamado implements Serializable{

	private static final long serialVersionUID = -2167880175960518943L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombreLlamado;
    
    @NotNull
    private Integer horas;
    
}
