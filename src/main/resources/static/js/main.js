
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

// SELECCIONA EL CLIENTE Y PASA LOS VALORES AL INPUT DEL FORM SERVICIO
function seleccionarCliente(cliente_id,cliente_dni_cuit,cliente_razonSocial, cliente_tel){
    $('#cliente').val(cliente_id);
    $('#cliente_ver').val(cliente_dni_cuit + ' - ' + cliente_razonSocial);
    $('#clienteTelefono').val(cliente_tel);
    $('#tabla-clientes').hide();
    $('#equipo').focus();    
}

function buscarSector(){

    $("html, body").animate({
        scrollTop: 400
    }, 600); 
    
    $(".lista-sectores").slideToggle();

  
}

// SELECCIONA EL SECTOR Y PASA LOS VALORES AL INPUT DEL FORM SERVICIO
function seleccionarSector(sector_id, sector_nombre){

    $('#sector').val(sector_id);
    $('#sector_ver').val(sector_nombre);
    $(".lista-sectores").slideToggle();
    $('#problemaReportado').focus();  

    $("html, body").animate({
        scrollTop: 150
    }, 600); 
    

}


$(document).ready(() => {

    if($('#cliente_id').val()){
        $('#cliente').val($('#cliente_id').val());
    }

    $(".lista-sectores").hide();
    $('.buscador').focus();
    $('#dniCuit').focus();

    // CAMPOS CLIENTE EN MAYUSCULA
    $('#razonSocial').focusout(()=>{
        $('#razonSocial').val($('#razonSocial').val().trim());
        $('#razonSocial').val($('#razonSocial').val().toUpperCase());
    });

    $('#contacto').focusout(()=>{
        $('#contacto').val($('#contacto').val().trim());
        $('#contacto').val($('#contacto').val().toUpperCase());
    });
    $('#email').focusout(()=>{
        $('#email').val($('#email').val().trim());
        $('#email').val($('#email').val().toUpperCase());
    });
    $('#web').focusout(()=>{
        $('#web').val($('#web').val().trim());
        $('#web').val($('#web').val().toUpperCase());
    });
    $('#direccion').focusout(()=>{
        $('#direccion').val($('#direccion').val().trim());
        $('#direccion').val($('#direccion').val().toUpperCase());
    });


    // CAMPOS SERVICIOS EN MAYUSCULA
    $('#equipo').focusout(()=>{
        $('#equipo').val($('#equipo').val().trim());
        $('#equipo').val($('#equipo').val().toUpperCase());
    });

    $('#problemaReportado').focusout(()=>{
        $('#problemaReportado').val($('#problemaReportado').val().trim());
        $('#problemaReportado').val($('#problemaReportado').val().toUpperCase());
    });
    $('#observaciones').focusout(()=>{
        $('#observaciones').val($('#observaciones').val().trim());
        $('#observaciones').val($('#observaciones').val().toUpperCase());
    });
    $('#solucion').focusout(()=>{
        $('#solucion').val($('#solucion').val().trim());
        $('#solucion').val($('#solucion').val().toUpperCase());
    });
    
    //CAMPO SECTOR EN MAYUSCULA
    
    $('#nombre').focusout(()=>{
        $('#nombre').val($('#nombre').val().trim());
        $('#nombre').val($('#nombre').val().toUpperCase());
    });


    // ASIGNA AUTOMATICAMENTE LA HORA Y FECHA AL CAMBIAR EL ESTADO A TERMINADO
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
  