package com.megasolution.app.sistemaintegral.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megasolution.app.sistemaintegral.models.respuestaJson.Localidad;
import com.megasolution.app.sistemaintegral.models.respuestaJson.Provincia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate fechaAlta;

    private String direccion;

    private String localidad;

    private String provincia;

    private String pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condicion_iva_id")
    @JsonIgnore
    private CondicionIva condicionIva;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Servicio> servicios;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", dniCuit='" + dniCuit + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", telefono=" + telefono +
                ", email='" + email + '\'' +
                '}';
    }

}
