package com.megasolution.app.sistemaintegral.utils;

public interface Constantes {

    int SIETE_DIAS_EN_MS = 604_800_000;
    int UNA_HORA_EN_MS = 3_600_000;

    String ACTIVE = "active";
    String PILL_ACTIVO = "pill_activo";
    String TITULO = "titulo";
    String TITULO_AGREGAR_SERVICIO = "Agregar servicio";
    String TITULO_EDITAR_SERVICIO = "Editar servicio";
    String TITULO_CLIENTES = "Clientes";
    String TITULO_AGREGAR_CLIENTE = "Agregar cliente";
    String TITULO_EDITAR_CLIENTE = "Editar cliente";
    String TITULO_SECTORES = "Sectores";
    String TITULO_AGREGAR_SECTOR = "Agregar sector";
    String TITULO_EDITAR_SECTOR = "Editar sector";
    String TITULO_USUARIOS = "Usuarios";

    String SERVICIO = "servicio";
    String SERVICIOS = "servicios";
    String CLIENTE = "cliente";
    String CLIENTES = "clientes";
    String SERVICIO_ID = "servicioId";
    String TELEFONO = "telefono";
    String ESTADOS = "estados";
    String SECTORES = "sectores";
    String SECTOR = "sector";
    String SUCCESS = "success";

    String ERROR = "error";
    String ALERT_DANGER_DNI_CUIT = "alertDangerDniCuit";
    String ERROR_DNI_CUIT = "errorDniCuit";
    String ESPACIO_ALERT_DANGER = " alert-danger";

    String MSJ_CLIENTE_NO_EXISTE = "El cliente no existe!";
    String MSJ_DNI_CUIT_EXISTENTE = "Ya existe un cliente con este número de documento!";
    String MSJ_CLIENTE_GUARDADO = "Cliente guardado con éxito!";
    String MSJ_CLIENTE_ACTUALIZADO = "Cliente actualizado con éxito!";
    String MSJ_CLIENTE_ELIMINADO = "Cliente eliminado con éxito!";
    Object MSJ_CLIENTE_NO_ENCONTRADO = "No se encontró ningún cliente!";

    String LOCALIDADES = "localidades";
    String PROVINCIAS = "provincias";
    String PAISES = "paises";

    String REDIRECT_CLIENTES = "redirect:/clientes";
    String ROOT = "";
    String BUSCAR = "buscar";
    String TODOS = "todos";
    String NUEVO = "nuevo";
    String GUARDAR = "guardar";
    String ELIMINAR_ID = "eliminar/{id}";

    String TEMPLATE_LISTA_CLIENTES = "clientes/lista";
    String TEMPLATE_FORM_CLIENTES = "clientes/form-cliente";

    String WARNING = "warning";
    String EDITAR_ID = "editar/{id}";

    String GET_CIUDADES = "get-ciudades/{provincia}";
    String EDITAR_GET_CIUDADES = "editar/get-ciudades/{provincia}";

    String LOCALIDADES_JSON = "localidades.json";
}
