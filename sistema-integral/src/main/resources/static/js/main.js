
//BORRAR CLIENTE SELECCIONADO
function borrarCliente(id, cliente_razonSocial){
    console.log($('#input_' + id).val());
    $('#borrarModal').modal('show');
    $('#borrarBoton').attr("href","/clientes/eliminar/" + id);
    $('.razonSocial').text(" '" +cliente_razonSocial+ "'");
    
    
}

// BORRAR SERVICIO SELECCIONADO
function borrarServicio(id, cliente_razonSocial){
    var estado = $('.estado_'+ id).text();
    console.log($('.estado_'+ id).text());
    console.log($('#input_' + id).val());
    $('#borrarModal').modal('show');
    $('#borrarBoton').attr("href","/servicios/eliminar/" + id);
    $('.razonSocial').text(" '" +cliente_razonSocial+ "'");
    $('.servicio_estado').text(" '" +estado+ "'");
}

//ABRE MODAL PARA BUSCAR CLIENTE
function buscarCliente(){
    $('#buscarClienteModal').modal('show');
}

// SELECCIONA EL CLIENTE Y PASA LOS VALORES AL INPUT DEL FORM SERVICIO
function seleccionarCliente(cliente_id,cliente_dni_cuit,cliente_razonSocial){
    $('#buscarClienteModal').modal('hide');
    console.log(cliente_razonSocial);
    console.log(cliente_dni_cuit);
    $('#cliente').val(cliente_id);
    $('#cliente_ver').val(cliente_dni_cuit + ' - ' + cliente_razonSocial);
    $('#equipo').focus();    

}

// ASIGNA AUTOMATICAMENTE LA HORA Y FECHA AL CAMBIAR EL ESTADO A TERMINADO
$(document).ready(() => {
    $('.buscador').focus();
    $('#dniCuit').focus();

    $('#estado').change(function() {
        var estadoSeleccionado = $(this).children('option:selected').val();
        moment.locale('es');

        if(estadoSeleccionado == 3){
            $('#fechaTerminado').val(moment().format('HH:mm DD/MM/YYYY'));
            
        }else{
            $('#fechaTerminado').val('');
        }
    
    })
})


// FUNCION PARA FILTRAR LA BUSQUEDA EN TABLAS
$(document).ready(function () {
 
    (function ($) {
 
        $('.buscador').keyup(function () {
 
             var rex = new RegExp($(this).val(), 'i'); // 'i' = ignoreCase
 
             $('.tabla tr').hide();
 
             $('.tabla tr').filter(function () {
               return rex.test($(this).text());
             }).show();
 
        })
 
    }(jQuery));
 
});
