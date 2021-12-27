package com.megasolution.app.sistemaintegral.models;

import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.respuestaJson.Localidad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClienteModel {

    List<Cliente> clientes;
    Cliente cliente;
    List<Localidad> localidades;
    List<Provincias> provincias;
    Paises paises;

    public ClienteModel(Cliente cliente){
        this.cliente = cliente;
    }

    public ClienteModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
