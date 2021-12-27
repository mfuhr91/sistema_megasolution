package com.megasolution.app.sistemaintegral.models.respuestaJson;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Localidad {
        private String categoria;
        private String fuente;
        private Municipio municipio;
        private Departamento departamento;
        private String nombre;
        private String id;
        private Provincia provincia;
        @JsonProperty("localidad_censal")
        private LocalidadCensal localidadCensal;
        private Centroide centroide;
}
