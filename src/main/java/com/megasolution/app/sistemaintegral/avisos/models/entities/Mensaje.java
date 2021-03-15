package com.megasolution.app.sistemaintegral.avisos.models.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mensajes")
@Data
@NoArgsConstructor
public class Mensaje implements Serializable{

	private static final long serialVersionUID = 122032045943662616L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_mensaje")
    private String tipoMensaje;

    @Column(name = "texto_mensaje")
    @NotBlank
    private String textoMensaje;

    @OneToMany(mappedBy = "mensaje",fetch = FetchType.LAZY)
    private List<Aviso> avisos;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "fecha_alta")
    private Date fechaAlta;

}
