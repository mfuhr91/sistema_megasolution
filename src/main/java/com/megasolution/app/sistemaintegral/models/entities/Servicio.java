package com.megasolution.app.sistemaintegral.models.entities;

import com.megasolution.app.sistemaintegral.utils.Estado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "servicios")
public class Servicio implements Serializable{

	private static final long serialVersionUID = -3889735587869995867L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String equipo;

    private Boolean cargador;
    
    private Boolean bateria;

    @Enumerated(value = EnumType.STRING)
    private Estado estado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", unique = true)
    private Sector sector;

    @NotBlank
    @Column(name = "problema_reportado", columnDefinition="text")
    private String problemaReportado;

    @Column(columnDefinition="text")
    private String observaciones;

    @Column(columnDefinition="text")
    private String solucion;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    private LocalDateTime fechaIngreso;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaTerminado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", equipo='" + equipo + '\'' +
                ", cargador=" + cargador +
                ", bateria=" + bateria +
                ", estado=" + estado +
                ", sector=" + sector +
                ", problemaReportado='" + problemaReportado + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", solucion='" + solucion + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaTerminado=" + fechaTerminado +
                ", cliente=" + cliente +
                '}';
    }
}
