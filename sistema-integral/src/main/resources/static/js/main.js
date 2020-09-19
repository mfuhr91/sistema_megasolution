
//BORRAR CLIENTE SELECCIONADO
function borrarCliente(id, cliente_razonSocial){
    $('#borrarModal').modal('show');
    $('#borrarBoton').attr("href","/clientes/eliminar/" + id);
    $('.razonSocial').text(" '" +cliente_razonSocial+ "'");
    
    
}

// BORRAR SERVICIO SELECCIONADO
function borrarServicio(id, cliente_razonSocial, estado){
    $('#borrarModal').modal('show');
    $('#borrarBoton').attr("href","/servicios/eliminar/" + id);
    $('.razonSocial').text(" '" +cliente_razonSocial+ "'");
    $('.servicio_estado').text(" '" +estado+ "'");
}

// BORRAR MENSAJE SELECCIONADO
function borrarMensaje(id){
    $('#borrarModal').modal('show');
    $('#borrarBoton').attr("href","/mensajes/eliminar/" + id);
}

//BORRAR CLIENTE SELECCIONADO
function borrarSector(id, nombre){
    $('#borrarModal').modal('show');
    $('#borrarBoton').attr("href","/sectores/eliminar/" + id);
    $('.nombre').text(" '" +nombre+ "'");
    
    
}
//BORRAR USUARIO SELECCIONADO
function borrarUsuario(id, nombreUsuario){
    $('#borrarModal').modal('show');
    $('#borrarBoton').attr("href","/usuarios/eliminar/" + id);
    $('.nombreUsuario').text(" '" +nombreUsuario+ "'");
    
    
}

//ABRE MODAL PARA BUSCAR CLIENTE
function buscarCliente(){
    $('#buscarClienteModal').modal('show');
}

// SELECCIONA EL CLIENTE Y PASA LOS VALORES AL INPUT DEL FORM SERVICIO
function seleccionarCliente(cliente_id,cliente_dni_cuit,cliente_razonSocial){
    $('#buscarClienteModal').modal('hide');
    $('#cliente').val(cliente_id);
    $('#cliente_ver').val(cliente_dni_cuit + ' - ' + cliente_razonSocial);
    $('#equipo').focus();    

}
//ABRE MODAL PARA BUSCAR SECTOR
function buscarSector(){
    $('#buscarSectorModal').modal('show');
}

// SELECCIONA EL SECTOR Y PASA LOS VALORES AL INPUT DEL FORM SERVICIO
function seleccionarSector(sector_id, sector_nombre){
    $('#buscarSectorModal').modal('hide');
    $('#sector').val(sector_id);
    $('#sector_ver').val(sector_nombre);
    $('#problemaReportado').focus();    

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
            
        }else if(estadoSeleccionado == 4){
            if(!$('#fechaTerminado').val()){
                $('#fechaTerminado').val(moment().format('HH:mm DD/MM/YYYY'));
            }else{
                $('#fechaTerminado').val();
            }
            $('#sector_ver').val('');

        }else{
            $('#fechaTerminado').val('');
        }
    
    })
})


// FUNCION PARA FILTRAR LA BUSQUEDA EN TABLAS
$(document).ready(() => {
 
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

// ELIMINA EL AVISO CAMBIANDO SU ESTADO A LEIDO
function cerrarAviso(id){
    $.post("/aviso-leido",{id})
    .done(function() {
        $('#alerta_' + id).fadeOut();
        $.get("/total-avisos", function(totalAvisos){
            $('#totalAvisosNoLeidos span').text(totalAvisos);
        })
    })
}

// DESHABILITA EL BOTON GUARDAR LUEGO DEL SUBMIT DEL FORM - EVITA GUARDAR DOS REGISTROS
$(document).ready(()=>{
    $('form').submit(function(){    
        $('input[type="submit"]').prop("disabled", true);
    });
});

// ANIMACION CONTADOR DEL DASHBOARD
$(document).ready(() => {
    $('.contar').each(function () {
      var $this = $(this);
      jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
        duration: 1000,
        easing: 'swing',
        step: function () {
          $this.text(Math.ceil(this.Counter));
        }
      });
    });
  })