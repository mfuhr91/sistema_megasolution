
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
    $.get("/servicios/mostrar-clientes",{
    }).done(function(clientes){
        console.log("ANDA");

        clientes.forEach(cliente => {
            console.log("ANDA");
            var fila = 
            `<tr>
            <td>
            <a id="seleccionar_`+ cliente.id +`" 
            onclick="seleccionarCliente('`+ cliente.id +`','`+ cliente.dniCuit +`','`+cliente.razonSocial +`')" 
            class="btn btn-primary mx-1"><i class="far fa-check-circle"></i></a>
            </td>
            <td>` + cliente.dniCuit + `</td>
            <td>` + cliente.razonSocial + `</td>
            <td>` + cliente.contacto + `</td>
            <td>` + cliente.telefono + `</td>
            <td>` + cliente.email + `</td>
            <td>` + cliente.direccion + `</td>
            <td>` + moment(cliente.fechaAlta).format('HH:mm DD/MM/YYYY') + `</td>
            <td>
            <a href="/clientes/editar/`+ cliente.id +`" class="btn btn-warning mx-1"><i class="far fa-edit"></i></a>
            </td>
            </tr>`;

            $('#tabla-clientes').append(fila);
        });
    })
    $('#buscarClienteModal').modal('show');
    $('#buscarClienteModal').on('shown.bs.modal', function(){
        $('.buscador').focus();
    });
    $('#buscarClienteModal').on('hidden.bs.modal', function(){
        $('.tabla').empty();
    });

    
}

// SELECCIONA EL CLIENTE Y PASA LOS VALORES AL INPUT DEL FORM SERVICIO
function seleccionarCliente(cliente_id,cliente_dni_cuit,cliente_razonSocial){
    $('#buscarClienteModal').modal('hide');
    $('#cliente').val(cliente_id);
    $('#cliente_ver').val(cliente_dni_cuit + ' - ' + cliente_razonSocial);
    $('.tabla tr').show();
    $('#equipo').focus();    

}
//ABRE MODAL PARA BUSCAR SECTOR
function buscarSector(){
    $.get("/servicios/mostrar-sectores",{
    }).done(function(sectores){
        sectores.forEach(sector => {
            var fila = 
            `<tr>
            <td>
            <a id="seleccionar_`+sector.id+`"
            onclick="seleccionarSector('`+sector.id+`', '`+sector.nombre +`')" 
            class="btn btn-primary mx-1"><i class="far fa-check-circle"></i></a>
            </td>
            <td>` + sector.nombre + `</td>
            <td>SI</td>
            <td>
            <a href="/sectores/editar/`+ sector.id +`" class="btn btn-warning mx-1"><i class="far fa-edit"></i></a>
            </td>`;

            $('#tabla-sectores').append(fila);
        });
        if($.isEmptyObject(sectores)){
                var fila = 
                `<td colspan="4" class="alert alert-warning">
                                        No hay sectores disponibles
                </td>`
                $('#tabla-sectores').append(fila);
        }
    })
    $('#buscarSectorModal').modal('show');
    $('#buscarSectorModal').on('shown.bs.modal', function(){
        $('.buscador').focus();
    });
    $('#buscarSectorModal').on('hidden.bs.modal', function(){
        $('.tabla').empty();
    });
}

// SELECCIONA EL SECTOR Y PASA LOS VALORES AL INPUT DEL FORM SERVICIO
function seleccionarSector(sector_id, sector_nombre){
    $('#buscarSectorModal').modal('hide');
    $('#sector').val(sector_id);
    $('#sector_ver').val(sector_nombre);
    $('.tabla tr').show();
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
      $({ Counter: 0 }).animate({ Counter: $this.text() }, {
        duration: 1000,
        easing: 'swing',
        step: function () {
          $this.text(Math.ceil(this.Counter));
        }
      });
    });
  })

  $(document).ready(()=>{
      $('#bateria').attr('checked',true); 
      $('#cargador').attr('checked',true); 
      $('#habilitado').attr('checked',true); 
      $('#bateria').click(function(){
        if($('#bateria').attr('checked',true)){
            $('#bateria').attr('checked',false);
        }
      });
      $('#bateria').click(function(){
        if($('#bateria').attr('checked',true)){
            $('#bateria').attr('checked',false);
        }
      });
      $('#habilitado').click(function(){
        if($('#habilitado').attr('checked',true)){
            $('#habilitado').attr('checked',false);
        }
      });
  })