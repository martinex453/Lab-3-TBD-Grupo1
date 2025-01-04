-- \connect cambia la conexión a la nueva base de datos
\c lab3tbd

-- Insertar categorías
INSERT INTO categoria (nombre) VALUES
('Tecnologia'),
('Ropa'),
('Hogar');


-- Insertar productos
INSERT INTO producto (nombre, descripcion, precio, stock, estado, id_categoria) VALUES
('Smartphone', 'Smartphone de ultima generacion', 500000, 50, 'disponible', 1),
('Laptop', 'Laptop para juegos', 1200000, 30, 'disponible', 1),
('Camiseta', 'Camiseta de algodon', 20000, 100, 'disponible', 2),
('Chaqueta', 'Chaqueta impermeable', 60000, 40, 'disponible', 2),
('Sofa', 'Sofa de tres cuerpos', 350000, 20, 'disponible', 3),
('Mesa', 'Mesa de comedor de madera', 200000, 15, 'disponible', 3),
('Tablet', 'Tablet con pantalla de alta resolucion', 250000, 60, 'disponible', 1),
('Auriculares', 'Auriculares inalambricos con cancelacion de ruido', 80000, 120, 'disponible', 1),
('Smartwatch', 'Reloj inteligente con monitor de actividad fisica', 180000, 70, 'disponible', 1),
('Camara', 'Camara digital profesional', 450000, 25, 'disponible', 1),
('Pantalones', 'Pantalones de mezclilla', 30000, 80, 'disponible', 2),
('Zapatos', 'Zapatos deportivos unisex', 50000, 60, 'disponible', 2),
('Gorro', 'Gorro de lana para invierno', 15000, 200, 'disponible', 2),
('Bufanda', 'Bufanda de tejido suave', 18000, 150, 'disponible', 2),
('Lampara', 'Lampara de escritorio con luz LED ajustable', 40000, 35, 'disponible', 3),
('Estanteria', 'Estanteria de madera para libros', 150000, 10, 'disponible', 3),
('Silla', 'Silla ergonomica para oficina', 120000, 25, 'disponible', 3),
('Alfombra', 'Alfombra decorativa de sala', 60000, 30, 'disponible', 3);


-- Insertar clientes
INSERT INTO cliente (nombre, direccion, email, telefono, contrasena, rol) VALUES
('Juan Perez', 'Calle Falsa 123', 'juan@example.com', '123456789', 'password123', 'cliente'),
('Ana Lopez', 'Avenida Siempre Viva 456', 'ana@example.com', '987654321', 'password456', 'cliente'),
('Carlos Garcia', 'Calle Real 789', 'carlos@example.com', '543216789', 'password789', 'cliente'),
('Lucia Martinez', 'Calle Luna 321', 'lucia@example.com', '321654987', 'password321', 'cliente'),
('Pedro Rodriguez', 'Calle Sol 456', 'pedro@example.com', '654987123', 'password987', 'cliente'),
('Marta Fernandez', 'Calle Estrella 789', 'marta@example.com', '876543210', 'password654', 'cliente'),
('Felipe Sanchez', 'Calle Rio 101', 'felipe@example.com', '112233445', 'password101', 'cliente');


-- Insertar repartidores
INSERT INTO repartidor (nombre, telefono) VALUES
('Luis Ramirez', '123456789'),
('Maria Gomez', '987654321'),
('Carlos Alvarez', '543216789'),
('Ana Martinez', '321654987');


-- Insertar órdenes
INSERT INTO orden (fecha_orden, estado, id_cliente, total, ubicacion_entrega) VALUES
('2024-09-01 10:00:00', 'enviada', 3, 500000, ST_SetSRID(ST_MakePoint(-70.6400, -33.4450), 4326)),
('2024-08-20 15:30:00', 'enviada', 4, 350000, ST_SetSRID(ST_MakePoint(-70.6050, -33.4300), 4326)),
('2024-11-20 09:00:00', 'enviada', 5, 1700000, ST_SetSRID(ST_MakePoint(-70.5600, -33.4100), 4326)),
('2024-11-20 12:30:00', 'enviada', 6, 80000, ST_SetSRID(ST_MakePoint(-70.6400, -33.4450), 4326)),
('2024-11-21 15:45:00', 'enviada', 5, 1700000, ST_SetSRID(ST_MakePoint(-70.6400, -33.4450), 4326)),
('2024-11-22 14:00:00', 'enviada', 6, 300000, ST_SetSRID(ST_MakePoint(-70.6050, -33.4300), 4326)),
('2024-11-23 17:30:00', 'enviada', 5, 200000, ST_SetSRID(ST_MakePoint(-70.5600, -33.4100), 4326)),
('2024-11-23 18:45:00', 'enviada', 6, 150000, ST_SetSRID(ST_MakePoint(-70.6050, -33.4300), 4326)),
('2024-11-24 10:00:00', 'enviada', 7, 1700000, ST_SetSRID(ST_MakePoint(-70.6400, -33.4450), 4326)),
('2024-11-24 11:30:00', 'enviada', 8, 1700000, ST_SetSRID(ST_MakePoint(-70.6050, -33.4300), 4326)),
('2024-11-25 09:30:00', 'enviada', 9, 1700000, ST_SetSRID(ST_MakePoint(-70.5600, -33.4100), 4326));


-- Insertar relaciones entre orden, zona y repartidor
INSERT INTO orden_repartidor (id_orden, id_repartidor) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 1),
(6, 2),
(7, 3),
(8, 4),
(9, 1),
(10, 2),
(11, 3);


-- Insertar detalles de órdenes
INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 500000),
(2, 5, 1, 350000),
(3, 1, 1, 500000),
(3, 2, 1, 1200000),
(4, 5, 1, 350000),
(5, 1, 1, 500000),
(5, 2, 1, 1200000),
(6, 5, 1, 350000),
(7, 3, 1, 20000),
(8, 1, 1, 500000),
(8, 3, 1, 20000),
(9, 1, 1, 500000),
(9, 2, 1, 1200000),
(10, 1, 1, 500000),
(10, 2, 1, 1200000),
(11, 1, 1, 500000),
(11, 2, 1, 1200000);


-- Insertar detalles de empresas
INSERT INTO empresa (rut, nombre) VALUES
('12345678-9', 'Empresa A'),
('98765432-1', 'Empresa B'),
('11223344-5', 'Empresa C'),
('55667788-0', 'Empresa D');


-- Insertar detalles de zonas entrega de empresas
INSERT INTO empresa_zona (id_zona, id_empresa) VALUES
(1, 1),
(2, 1),
(1, 2),
(2, 3),
(1, 4),
(3, 4);
