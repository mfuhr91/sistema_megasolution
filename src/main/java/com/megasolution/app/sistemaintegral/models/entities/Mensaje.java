package com.megasolution.app.sistemaintegral.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "mensajes")
@Getter
@Setter
@NoArgsConstructor
public class Mensaje implements Serializable{

	private static final long serialVersionUID = 122032045943662616L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipoMensaje;

    @NotBlank
    private String textoMensaje;

    @OneToMany(mappedBy = "mensaje",fetch = FetchType.LAZY)
    private List<Aviso> avisos;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaAlta;

}
