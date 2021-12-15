SET NAMES 'utf8';
DROP DATABASE IF EXISTS ventas;
CREATE DATABASE IF NOT EXISTS ventas DEFAULT CHARACTER SET utf8;
USE ventas;

/*Clientes*/

CREATE TABLE clientes(
id_clientes					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_clientes				VARCHAR(25) NOT NULL, 
apellido_clientes			VARCHAR(25) NOT NULL
)DEFAULT CHARACTER SET utf8;

CREATE VIEW todosclientes AS SELECT * FROM clientes;

DELIMITER $$
CREATE TRIGGER clientes_mayus BEFORE INSERT ON clientes FOR EACH ROW
BEGIN
	SET NEW.nombre_clientes = UPPER(NEW.nombre_clientes);
	SET NEW.apellido_clientes = UPPER(NEW.apellido_clientes);
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER clientes_mayus_up BEFORE UPDATE ON clientes FOR EACH ROW
BEGIN
	SET NEW.nombre_clientes = UPPER(NEW.nombre_clientes);
	SET NEW.apellido_clientes = UPPER(NEW.apellido_clientes);
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE todosClientes()
BEGIN
	SELECT * FROM todosClientes;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarClientes(IN nombre varchar(25), IN apellido varchar(25))
BEGIN
	INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES(nombre,apellido);
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE eliminarClientes(IN id int)
BEGIN
	DELETE FROM clientes WHERE id_clientes=id;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE modificarClientes(IN id int, IN nombre varchar(25), IN apellido varchar(25))
BEGIN
	UPDATE clientes SET nombre_clientes=nombre,apellido_clientes=apellido WHERE id_clientes=id;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE buscarClientes(IN patron varchar(15))
BEGIN
	SELECT * FROM clientes WHERE nombre_clientes LIKE CONCAT('%',patron,'%');
END $$
DELIMITER ;

INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Javier','Gonzalez');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Josan','Castillo');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Christian','Manzano');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Nikola','Tesla');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Galileo','Galilei');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Carlos','Marx');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Tomas','A.Eddison');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Antonio','Vivaldi');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Clerk','Maxwell');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Alexandro','Volta');

/*Facturas*/

CREATE TABLE facturas(
id_facturas					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
id_clientes					INTEGER NOT NULL,
referencia_facturas		    VARCHAR(40) NOT NULL,
fecha_facturas				DATE NOT NULL, 
FOREIGN KEY(id_clientes) REFERENCES clientes(id_clientes) ON DELETE CASCADE,
CONSTRAINT  referencia_repetida UNIQUE(referencia_facturas)
)DEFAULT CHARACTER SET utf8;

DELIMITER $$
CREATE TRIGGER facturas_mayus BEFORE INSERT ON facturas FOR EACH ROW
BEGIN
	SET NEW.referencia_facturas = UPPER(NEW.referencia_facturas);
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER facturas_mayus_up BEFORE UPDATE ON facturas FOR EACH ROW
BEGIN
	SET NEW.referencia_facturas = UPPER(NEW.referencia_facturas);
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE todasFacturas()
BEGIN
	SELECT a.id_facturas, a.referencia_facturas, a.fecha_facturas, b.id_clientes, b.nombre_clientes, b.apellido_clientes FROM facturas a, clientes b WHERE a.id_clientes=b.id_clientes;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE eliminarFacturas(IN id int)
BEGIN
	DELETE FROM facturas WHERE id_facturas=id;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE modificarFacturas(IN id int, IN idCliente int, IN referencia varchar(40), IN fecha DATE)
BEGIN
	UPDATE facturas SET id_clientes=idCliente,referencia_facturas=referencia, fecha_facturas=fecha WHERE id_facturas=id;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarFacturas(IN idCliente int, IN referencia varchar(40), IN fecha DATE)
BEGIN
	INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) VALUES(idCliente, referencia, fecha);
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE buscarFacturas(IN patron varchar(15))
BEGIN
	SELECT a.id_facturas, a.referencia_facturas, a.fecha_facturas, b.id_clientes, b.nombre_clientes, b.apellido_clientes FROM facturas a, clientes b WHERE a.id_clientes=b.id_clientes AND a.referencia_facturas LIKE CONCAT('%',patron,'%');
END $$
DELIMITER ;

INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(1,'FAC1224',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(2,'FAC1225',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(3,'FAC1226',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(4,'FAC1227',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(5,'FAC1228',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(6,'FAC1229',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(7,'FAC1301',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(8,'FAC1302',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(9,'FAC1303',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(10,'FAC1304',NOW());

/*Productos*/

CREATE TABLE productos(
id_productos				INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_productos			VARCHAR(80) NOT NULL, 
precio_productos			DOUBLE NOT NULL, 
CONSTRAINT precio_mal CHECK (precio_productos > 0) 
)DEFAULT CHARACTER SET utf8;

CREATE VIEW todosproductos AS SELECT * FROM productos;

DELIMITER $$
CREATE TRIGGER productos_mayus BEFORE INSERT ON productos FOR EACH ROW
BEGIN
	SET NEW.nombre_productos = UPPER(NEW.nombre_productos);  
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER productos_mayus_up BEFORE UPDATE ON productos FOR EACH ROW
BEGIN
	SET NEW.nombre_productos = UPPER(NEW.nombre_productos);  
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE todosProductos()
BEGIN
	SELECT * FROM todosProductos;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarProductos(IN nombre varchar(25), IN precio double)
BEGIN
	INSERT INTO productos(nombre_productos,precio_productos) VALUES(nombre,precio);
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE eliminarProductos(IN id int)
BEGIN
	DELETE FROM productos WHERE id_productos=id;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE modificarProductos(IN id int, IN nombre varchar(25), IN precio double)
BEGIN
	UPDATE productos SET nombre_productos=nombre,precio_productos=precio WHERE id_productos=id;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE buscarProductos(IN patron varchar(15))
BEGIN
	SELECT * FROM productos WHERE nombre_productos LIKE CONCAT('%',patron,'%');
END $$
DELIMITER ;

INSERT INTO productos(nombre_productos,precio_productos) VALUES('Cubrebocas',10);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Chocolate',10.23);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Lupa',15.50);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Compás',25.77);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Protoboard',50.30);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Multimetro',100.50);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('VR Box',300.30);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Lampara Tesla',499.99);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('SSD',1199.99);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Piano',12000);

/* Facturas - Productos */

CREATE TABLE facturas_productos(
id_facturas					INTEGER NOT NULL,
id_productos				INTEGER NOT NULL,
cantidad_facturas_productos	DOUBLE NOT NULL,
CONSTRAINT unica_fac_prod PRIMARY KEY(id_facturas,id_productos),
FOREIGN KEY(id_facturas) REFERENCES facturas(id_facturas) ON DELETE CASCADE,
FOREIGN KEY(id_productos) REFERENCES productos(id_productos) ON DELETE CASCADE,
CONSTRAINT cantidad_mal CHECK (cantidad_facturas_productos > 0)
)DEFAULT CHARACTER SET utf8;

DELIMITER $$
CREATE  PROCEDURE todosFacProd()
BEGIN
	SELECT a.id_facturas, a.referencia_facturas, a.fecha_facturas, d.id_clientes, d.nombre_clientes, d.apellido_clientes, b.id_productos, b.nombre_productos, b.precio_productos, c.cantidad_facturas_productos FROM facturas a, productos b, facturas_productos c, clientes d WHERE a.id_facturas = c.id_facturas AND b.id_productos = c.id_productos AND a.id_clientes=d.id_clientes;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarFacProd(IN idFactura INT, IN idProducto INT, IN cantidad DOUBLE)
BEGIN
	INSERT INTO facturas_productos(id_facturas, id_productos, cantidad_facturas_productos) VALUES(idFactura, idProducto, cantidad);
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE eliminarFacProd(IN idFac INT, IN idProd INT)
BEGIN
	DELETE FROM facturas_productos WHERE id_facturas = idFac AND id_productos = idProd;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE modificarFacProd(IN idFactura INT, IN idProducto INT, IN cantidad DOUBLE)
BEGIN
	UPDATE facturas_productos SET id_facturas=idFactura, id_productos=idProducto, cantidad_facturas_productos=cantidad WHERE id_facturas = idFactura AND id_productos = idProducto;
END $$
DELIMITER ;

INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,1,500);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,2,50);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(3,3,25);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(4,4,85);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(5,5,7);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(6,6,10);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(7,7,25);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(8,8,5);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(9,9,2);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(10,10,1); 