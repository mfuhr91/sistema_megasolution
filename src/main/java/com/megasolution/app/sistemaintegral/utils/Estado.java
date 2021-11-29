package com.megasolution.app.sistemaintegral.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Estado {

    PENDIENTE("Pendiente","PENDIENTE"),
    EN_PROCESO("En Proceso","EN_PROCESO"),
    TERMINADO("Terminado","TERMINADO"),
    ENTREGADO("Entregado","ENTREGADO"),
    GUARDADO("Guardado","GUARDADO"),
    ENVIADO("Enviado","ENVIADO"),
    NO_ENVIADO("No Enviado","NO_ENVIADO");

    private String nombre;
    private String valor;

    public static List<Estado> getEstadosServicios(){
        return Arrays.stream(Estado.values())
                .filter(estado -> !estado.equals(Estado.ENVIADO) && !estado.equals(Estado.NO_ENVIADO))
                .collect(Collectors.toList());
    }
}
