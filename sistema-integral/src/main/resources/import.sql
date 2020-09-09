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


INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook HP', TRUE, TRUE, 1, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus rhoncus auctor sem, non sollicitudin ex suscipit nec.', 'tapa rota', '', '2020-08-19 10:35', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook Dell', false, false, 1, 3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus rhoncus auctor sem, non sollicitudin ex suscipit nec.', 'tapa rota', '', '2020-08-19 11:13', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook HP', true, false, 2, 4, 'No funciona pantalla', 'tapa rota', '', '2020-08-19 19:01', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook HP', true, false, 2, 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus rhoncus auctor sem, non sollicitudin ex suscipit nec.', 'tapa rota', '', '2020-08-19 16:56', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook Lenovo', false, true, 3, 2, 'No funciona pantalla', 'tapa rota', ' Pellentesque in purus luctus, lobortis ex id, volutpat lectus.', '2020-08-19 17:04', 1);
INSERT INTO servicios (equipo, cargador, bateria, estado_id, sector, problema_reportado, observaciones, solucion, fecha_ingreso, cliente_id) VALUES ('Notebook Lenovo', false, true, 2, 2, 'No funciona pantalla', 'tapa rota', ' Pellentesque in purus luctus, lobortis ex id, volutpat lectus.', '2020-08-19 20:01', 3);
