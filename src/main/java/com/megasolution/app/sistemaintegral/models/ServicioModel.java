package com.megasolution.app.sistemaintegral.models;

import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.utils.Estado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ServicioModel {

    private List<Servicio> servicios;
    private Cliente cliente;
    private List<Estado> estados;
    private Servicio servicio;
    private String estado;

    public ServicioModel(Servicio servicio, Cliente cliente) {
        this.servicio = servicio;
        this.cliente = cliente;
    }
}
