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
    String ID = "id";

    String ERROR = "error";
    String ALERT_DANGER_DNI_CUIT = "alertDangerDniCuit";
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

    String LOCALIDADES = "localidades";
    String PROVINCIAS = "provincias";
    String PAISES = "paises";

    String REDIRECT_CLIENTES = "redirect:/clientes";
    String REDIRECT_SERVICIOS = "redirect:/servicios";

    String BUSCAR = "buscar";
    String TODOS = "todos";
    String NUEVO = "nuevo";
    String GUARDAR = "guardar";
    String ELIMINAR_ID = "eliminar/{id}";
    String MONITOR = "monitor";

    String TEMPLATE_LISTA_CLIENTES = "clientes/lista";
    String TEMPLATE_FORM_CLIENTES = "clientes/form-cliente";

    String TEMPLATE_LISTA_SERVICIOS = "servicios/lista";
    String TEMPLATE_FORM_SERVICIOS = "servicios/form-servicio";

    // debe quedar con el "/servicios/form-servicios" ya que no es una vista html, sino la ruta de un componente clase
    String PATH_CLASE_JAVA_PDF = "/servicios/form-servicio";

    String WARNING = "warning";
    String EDITAR_ID = "editar/{id}";

    String GET_CIUDADES = "get-ciudades/{provincia}";
    String EDITAR_GET_CIUDADES = "editar/get-ciudades/{provincia}";

    String LOCALIDADES_JSON = "localidades.json";
    String PENDIENTE = "pendiente";
    String EN_PROCESO = "en-proceso";
    String TERMINADO = "terminado";
    String ENTREGADO = "entregado";
    String GUARDADO = "guardado";
    String CLIENTE_ID = "cliente/{id}";
    String PENDIENTE_CLIENTE_ID = "pendiente/cliente/{id}";
    String EN_PROCESO_CLIENTE_ID = "en-proceso/cliente/{id}";
    String TERMINADO_CLIENTE_ID = "terminado/cliente/{id}";
    String ENTREGADO_CLIENTE_ID = "entregado/cliente/{id}";
    String GUARDADO_CLIENTE_ID = "guardado/cliente/{id}";
    String IMPRIMIR_SERVICIO_ID = "imprimir/{id}";
    String SUCCESS_IMPRIMIR = "successImprimir";
}
