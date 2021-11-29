package com.megasolution.app.sistemaintegral.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "avisos")
@Getter
@Setter
@NoArgsConstructor
public class Aviso implements Serializable{
 
	private static final long serialVersionUID = -8098425738271256629L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mensaje_id")
    private Mensaje mensaje;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
    
    private Boolean leido;

    private LocalDate fechaAlta;

    private LocalDate fechaLeido;

    @ManyToOne // NO PUEDE SER UN LAZY FETCH POR QUE NO LEE LOS LLAMADOS LEIDOS
    @JoinColumn(name = "llamado_id")
    private Llamado llamado;

    public Aviso(String nombre, Mensaje mensaje, Servicio servicio, Llamado llamado) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.servicio = servicio;
        this.leido = false;
        this.fechaAlta = LocalDate.now();
        this.llamado = llamado;
    }

    
}
