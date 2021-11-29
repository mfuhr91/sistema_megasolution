package com.megasolution.app.sistemaintegral.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoMail {

    SERVICIO_TERMINADO("servicio_terminado"),
    VALORACION("valoracion");

    private String tipo;

}
