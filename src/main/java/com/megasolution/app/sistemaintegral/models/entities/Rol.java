package com.megasolution.app.sistemaintegral.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
public class Rol implements Serializable{

	private static final long serialVersionUID = 5242932754278202984L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String authority;
    
}