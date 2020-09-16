INSERT INTO paises (nombre) VALUES ("Argentina");

INSERT INTO provincias (nombre, pais_id) VALUES ("Tierra del fuego",1);
INSERT INTO provincias (nombre, pais_id) VALUES ("Santa Cruz",1);

INSERT INTO localidades (nombre, provincia_id) VALUES("Ushuaia",1);
INSERT INTO localidades (nombre, provincia_id) VALUES("Tolhuin",1);
INSERT INTO localidades (nombre, provincia_id) VALUES("Rio Grande",1);

INSERT INTO condiciones_iva (nombre, porcentaje) VALUES ("consumidor final", 0.0);
INSERT INTO condiciones_iva (nombre, porcentaje) VALUES ("exento", 0.0);
INSERT INTO condiciones_iva (nombre, porcentaje) VALUES ("monotributista", 0.0);

INSERT INTO clientes (dni_cuit, razon_social, telefono, fecha_alta, condicion_iva_id, direccion, localidad_id, provincia_id, pais_id, email) VALUES (35356600, 'Mariano Fuhr',2901538259, '2020-08-19', 1, "Pájaro Carpintero 521 dpto 4" , 1, 1, 1, 'mfuhr@gmail.com');
INSERT INTO clientes (dni_cuit, razon_social, telefono, fecha_alta, condicion_iva_id, direccion, localidad_id, provincia_id, pais_id, email) VALUES (32134220, 'Pablo Mengano',2901538259, '2020-08-19', 1, "Pájaro Carpintero 521 dpto 4" , 3, 1, 1, 'mfuhr@gmail.com');
INSERT INTO clientes (dni_cuit, razon_social, telefono, fecha_alta, condicion_iva_id, direccion, localidad_id, provincia_id, pais_id, email) VALUES (23242144, 'Juan Fulano',2901538259, '2020-08-19',  1, "Pájaro Carpintero 521 dpto 4" , 2, 1, 1, 'mfuhr@gmail.com');
INSERT INTO clientes (dni_cuit, razon_social, telefono, fecha_alta, condicion_iva_id, direccion, localidad_id, provincia_id, pais_id, email) VALUES (12345243, 'Elite SRL',2901538259, '2020-08-19',  1, "Pájaro Carpintero 521 dpto 4" , 1, 1, 1, 'mfuhr@gmail.com');



INSERT INTO estados (nombre) VALUES ('Pendiente');
INSERT INTO estados (nombre) VALUES ('En Proceso');
INSERT INTO estados (nombre) VALUES ('Terminado');
INSERT INTO estados (nombre) VALUES ('Entregado');

INSERT INTO sectores (nombre, disponible) VALUES ('1A', false);
INSERT INTO sectores (nombre, disponible) VALUES ('1B', false);
INSERT INTO sectores (nombre, disponible) VALUES ('1C', false);
INSERT INTO sectores (nombre, disponible) VALUES ('2A', false);
INSERT INTO sectores (nombre, disponible) VALUES ('2B', false);
INSERT INTO sectores (nombre, disponible) VALUES ('2C', false);
INSERT INTO sectores (nombre, disponible) VALUES ('3A', true);


INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector_id, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook HP', TRUE, TRUE, 1, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus rhoncus auctor sem, non sollicitudin ex suscipit nec.', 'tapa rota', '', '2020-08-19 10:35', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector_id, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook Dell', false, false, 1, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus rhoncus auctor sem, non sollicitudin ex suscipit nec.', 'tapa rota', '', '2020-08-19 11:13', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector_id, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook HP', true, false, 2, 3, 'No funciona pantalla', 'tapa rota', '', '2020-08-19 19:01', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector_id, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook HP', true, false, 2, 4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus rhoncus auctor sem, non sollicitudin ex suscipit nec.', 'tapa rota', '', '2020-08-19 16:56', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector_id, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook Lenovo', false, true, 3, 5, 'No funciona pantalla', 'tapa rota', ' Pellentesque in purus luctus, lobortis ex id, volutpat lectus.', '2020-08-19 17:04', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector_id, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook Lenovo', false, true, 3, 6, 'No funciona pantalla', 'tapa rota', 'OK', '2020-08-19 20:01', 3);


/*INSERT INTO llamados (nombre_llamado, horas) VALUES ('1er llamado',0);*/
INSERT INTO llamados (nombre_llamado, horas) VALUES ('2do llamado',1);
INSERT INTO llamados (nombre_llamado, horas) VALUES ('3er llamado',3);

INSERT INTO mensajes (tipo_mensaje, texto_mensaje , fecha_alta) VALUES ('servicio_terminado','Servicio terminado', '2020-08-19')

INSERT INTO avisos (nombre, mensaje_id, servicio_id, llamado_id, leido, fecha_alta ) VALUES ('servicio_terminado', 1, 5, 1, false, '2020-08-19 14:00'); 
INSERT INTO avisos (nombre, mensaje_id, servicio_id, llamado_id, leido, fecha_alta ) VALUES ('servicio_terminado', 1, 6, 1,  false, '2020-08-19 14:00'); 
