package com.megasolution.app.sistemaintegral.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megasolution.app.sistemaintegral.models.respuestaJson.Localidad;
import com.megasolution.app.sistemaintegral.models.respuestaJson.RespuestaJson;
import com.megasolution.app.sistemaintegral.utils.Constantes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.Configuration;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Provincias {

    TIERRA_DEL_FUEGO("TIERRA DEL FUEGO"),
    SANTA_CRUZ("SANTA CRUZ"),
    CHUBUT("CHUBUT"),
    RIO_NEGRO("RÍO NEGRO"),
    NEUQUEN("NEUQUÉN"),
    LA_PAMPA("LA PAMPA"),
    BUENOS_AIRES("BUENOS AIRES"),
    MENDOZA("MENDOZA"),
    SAN_LUIS("SAN LUIS"),
    CORDOBA("CÓRDOBA"),
    SANTA_FE("SANTA FE"),
    ENTRE_RIOS("ENTRE RÍOS"),
    SAN_JUAN("SAN JUAN"),
    LA_RIOJA("LA RIOJA"),
    SANTIAGO_DEL_ESTERO("SANTIAGO DEL ESTERO"),
    CATAMARCA("CATAMARCA"),
    TUCUMAN("TUCUMÁN"),
    CORRIENTES("CORRIENTES"),
    MISIONES("MISIONES"),
    CHACO("CHACO"),
    SALTA("SALTA"),
    FORMOSA("FORMOSA"),
    JUJUY("JUJUY");

    String nombre;

    public static List<Provincias> getProvincias() {
        return Arrays.stream(Provincias.values()).collect(Collectors.toList());
    }

    public static List<Localidad> getCiudadesDeProvincia(String provincia) throws IOException {

        final ObjectMapper JSON_MAPPER = new ObjectMapper();
       /* Resource file = new ClassPathResource(Constantes.LOCALIDADES_JSON);

        RespuestaJson respuestaJson = JSON_MAPPER.readValue(new File(String.valueOf(file.getFile())), RespuestaJson.class);*/

        InputStream initialStream = new FileInputStream(System.getenv("HOME").concat("/").concat(Constantes.LOCALIDADES_JSON));
        File targetFile = new File(Constantes.LOCALIDADES_JSON);

        java.nio.file.Files.copy(
                initialStream,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        IOUtils.closeQuietly(initialStream);

        RespuestaJson respuestaJson = JSON_MAPPER.readValue(new File(targetFile.getPath()), RespuestaJson.class);
        List<Localidad> localidades = Arrays.stream(respuestaJson.getLocalidades())
                .filter(ciudad -> ciudad.getProvincia().getNombre().toLowerCase().contains(provincia.toLowerCase())).collect(Collectors.toList());

        if(provincia.equals(Provincias.TIERRA_DEL_FUEGO.getNombre())){
            localidades.sort(Comparator.comparing(Localidad::getNombre).reversed());
        }

        return localidades;
    }
}
