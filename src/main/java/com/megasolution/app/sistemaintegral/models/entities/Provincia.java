package com.megasolution.app.sistemaintegral.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "provincias")
@Getter
@Setter
@NoArgsConstructor
public class Provincia implements Serializable{

	private static final long serialVersionUID = 5644264858532693819L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id")
    private Pais pais;


}
