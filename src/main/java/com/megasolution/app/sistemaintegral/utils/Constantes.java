package com.megasolution.app.sistemaintegral.utils;

public interface Constantes {

    int SIETE_DIAS_EN_MS = 604_800_000;
    int UNA_HORA_EN_MS = 3_600_000;

    String ACTIVE = "active";
    String PILL_ACTIVO = "pill_activo";
    String TITULO = "titulo";
    String TITULO_SERVICIOS = "Servicios";
    String TITULO_AGREGAR_SERVICIO = "Agregar servicio";
    String TITULO_EDITAR_SERVICIO = "Editar servicio";
    String TITULO_CLIENTES = "Clientes";
    String TITULO_AGREGAR_CLIENTE = "Agregar cliente";
    String TITULO_EDITAR_CLIENTE = "Editar cliente";
    String TITULO_VISUALIZADOR_DE_SERVICIOS = "Visualizador de Servicios";

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
    String ID = "id";

    String ERROR = "error";

    String ALERT_DANGER_DNI_CUIT = "alertDangerDniCuit";
    String ALERT_DANGER_SOLUCION = "alertDangerSolucion";
    String ALERT_DANGER_CLIENTE = "alertDangerCliente";
    String ALERT_DANGER_SECTOR = "alertDangerSector";

    String ERROR_DNI_CUIT = "errorDniCuit";
    String ERROR_SOLUCION = "errorSolucion";
    String ERROR_CLIENTE = "errorCliente";
    String ERROR_SECTOR = "errorSector";
    String ESPACIO_ALERT_DANGER = " alert-danger";


    String MSJ_CLIENTE_NO_EXISTE = "El cliente no existe!";
    String MSJ_DNI_CUIT_EXISTENTE = "Ya existe un cliente con este número de documento!";
    String MSJ_CLIENTE_GUARDADO = "Cliente guardado con éxito!";
    String MSJ_CLIENTE_ACTUALIZADO = "Cliente actualizado con éxito!";
    String MSJ_CLIENTE_ELIMINADO = "Cliente eliminado con éxito!";
    String MSJ_CLIENTE_NO_ENCONTRADO = "No se encontró ningún cliente!";

    String MSJ_SERVICIO_GUARDADO = "Servicio guardado con éxito!";
    String MSJ_SERVICIO_ACTUALIZADO = "Servicio actualizado con éxito!";
    String MSJ_SERVICIO_ELIMINADO = "Servicio eliminado con éxito!";
    String MSJ_SERVICIO_NO_EXISTE = "El servicio no existe!";
    String MSJ_SERVICIO_NO_ENCONTRADO = "No se encontró ningún servicio!";
    String MSJ_INGRESAR_SOLUCION = "Debe ingresar una solución antes de guardar el servicio!";
    String MSJ_INGRESAR_CLIENTE = "Debe seleccionar un cliente antes de guardar el servicio!";
    String MSJ_INGRESAR_SECTOR = "Debe seleccionar un sector antes de guardar el servicio!";
    String MSJ_SECTOR_NO_DISPONIBLE = "Debe seleccionar un sector disponible";

    String LOCALIDADES = "localidades";
    String PROVINCIAS = "provincias";
    String PAISES = "paises";

    String REDIRECT_CLIENTES = "redirect:/clientes";
    String REDIRECT_SERVICIOS = "redirect:/servicios";
    String REDIRECT_SERVICIOS_NUEVO = "redirect:/servicios/nuevo";
    String REDIRECT_SERVICIOS_EDITAR = "redirect:/servicios/editar/{id}";

    String TODOS = "todos";

    String WARNING = "warning";

    String LOCALIDADES_JSON = "localidades.json";
    String PENDIENTE = "pendiente";
    String EN_PROCESO = "en_proceso";
    String TERMINADO = "terminado";
    String ENTREGADO = "entregado";
    String GUARDADO = "guardado";

    String SUCCESS_IMPRIMIR = "successImprimir";
    String TOTAL_PENDIENTES = "totalPendientes";
    String TOTAL_EN_PROCESO = "totalEnProceso";
    String SERVICIOS_PENDIENTES = "serviciosPendientes";
    String SERVICIOS_EN_PROCESO = "serviciosEnProceso";

}
