package com.megasolution.app.sistemaintegral.usuarios.models.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_usuario", unique = true)
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String contraseña;

    @Column(name = "nombre_completo")
    @NotBlank
    private String nombreCompleto;

    private Boolean habilitado;

    @Temporal(value = TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "HH:mm dd/MM/yyyy")
    @Column(name = "fecha_alta")
    private Date fechaAlta;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rol_id")
    private Rol rol;
}
