package com.megasolution.app.sistemaintegral.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable{

	private static final long serialVersionUID = -749881834291498513L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(1000000L)
    @Max(99999999999L)
    private Long dniCuit;

    private String razonSocial;

    private String contacto;    

    @NotNull
    private Long telefono;

    @Email
    @NotBlank
    private String email;

    private String web;

    @DateTimeFormat(pattern = "HH:mm dd/MM/yyyy")
    @NotNull
    private LocalDateTime fechaAlta;

    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad_id")
    @JsonIgnore
    private Localidad localidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia_id")
    @JsonIgnore
    private Provincia provincia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id")
    @JsonIgnore
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condicion_iva_id")
    @JsonIgnore
    private CondicionIva condicionIva;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Servicio> servicios; 



}
