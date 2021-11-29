package com.megasolution.app.sistemaintegral.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements Serializable{
    
	private static final long serialVersionUID = -6479715429121360652L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_usuario", unique = true)
    @NotBlank
    private String nombreUsuario;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contraseña;

    @Column(name = "nombre_completo")
    @NotBlank
    private String nombreCompleto;

    private Boolean habilitado;

    @DateTimeFormat(pattern = "HH:mm dd/MM/yyyy")
    @Column(name = "fecha_alta")
    @NotNull
    private LocalDateTime fechaAlta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id")
    private Rol rol;
}
