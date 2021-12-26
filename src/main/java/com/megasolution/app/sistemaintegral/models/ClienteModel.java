package com.megasolution.app.sistemaintegral.models;

import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Localidad;
import com.megasolution.app.sistemaintegral.models.entities.Pais;
import com.megasolution.app.sistemaintegral.models.entities.Provincia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ui.Model;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClienteModel {

    List<Cliente> clientes;
    Cliente cliente;
    List<Localidad> localidades;
    List<Provincia> provincias;
    List<Pais> paises;

    public ClienteModel(Cliente cliente){
        this.cliente = cliente;
    }

    public ClienteModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
