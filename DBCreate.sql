-- Parte 1: Crear la Base de Datos
CREATE DATABASE "lab3tbd"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TEMPLATE = template0;
    LC_COLLATE = 'Spanish_Chile.1252'
    LC_CTYPE = 'Spanish_Chile.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Parte 2: Conectar a la base de datos y crear las tablas
\connect lab3tbd;
CREATE EXTENSION IF NOT EXISTS postgis;


-- 1. Crear tabla categoría
CREATE TABLE categoria (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- 2. Crear tabla cliente
CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    contrasena VARCHAR(90) NOT NULL,
    rol VARCHAR(50) CHECK (rol IN ('admin', 'cliente')) NOT NULL
);

-- 3. Crear tabla producto
CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    estado VARCHAR(50) CHECK (estado IN ('disponible', 'agotado')) NOT NULL,
    id_categoria INTEGER,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria) ON DELETE SET NULL
);

-- 4. Crear tabla zonas
CREATE TABLE zona (
    id_zona SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) CHECK (tipo IN ('reparto', 'restringida')) NOT NULL,
    zona_geom GEOMETRY(POLYGON, 4326) NOT NULL
);

-- 5. Crear tabla repartidor
CREATE TABLE repartidor (
    id_repartidor SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(20)
);

-- 6. Crear tabla orden
CREATE TABLE orden (
    id_orden SERIAL PRIMARY KEY,
    fecha_orden TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50) CHECK (estado IN ('pendiente', 'pagada', 'enviada')) NOT NULL,
    id_cliente INTEGER,
    total DECIMAL(10, 2) NOT NULL,
    ubicacion_entrega GEOMETRY(POINT, 4326),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE SET NULL
);

-- 7. Crear la tabla orden_zona_repartidor
CREATE TABLE orden_repartidor (
    id SERIAL PRIMARY KEY,
    id_orden INTEGER NOT NULL,
    id_repartidor INTEGER NOT NULL,
    FOREIGN KEY (id_orden) REFERENCES orden(id_orden) ON DELETE CASCADE,
    FOREIGN KEY (id_repartidor) REFERENCES repartidor(id_repartidor) ON DELETE SET NULL
);

-- 8. Crear la tabla detalle_orden 
CREATE TABLE detalle_orden (
    id_detalle SERIAL PRIMARY KEY,
    id_orden INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_orden) REFERENCES orden(id_orden) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE SET NULL
);

-- 9. Crear tabla cliente_Sesion
CREATE TABLE cliente_sesion (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL
);

-- 10. Crear tabla empresa
CREATE TABLE empresa (
    id_empresa SERIAL PRIMARY KEY,  
    rut VARCHAR(20) NOT NULL, 
    nombre VARCHAR(100) NOT NULL
);

-- 11. Crear tabla empresa_zona
CREATE TABLE empresa_zona (
    id_empresa_zona SERIAL PRIMARY KEY,  
    id_zona INTEGER NOT NULL,
    id_empresa INTEGER NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa) ON DELETE CASCADE,
    FOREIGN KEY (id_zona) REFERENCES zona(id_zona) ON DELETE CASCADE
);

-- Crear índices espaciales
CREATE INDEX idx_zonas_geom ON zona USING GIST (zona_geom);
CREATE INDEX idx_orden_geom ON orden USING GIST (ubicacion_entrega);


-- Tabla trigger
CREATE TABLE detalles_querys (
    id_detalle SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,       -- Usuario que realizó la operación
    operacion VARCHAR(10) NOT NULL,      -- Tipo de operación (INSERT, UPDATE, DELETE)
    tabla_afectada VARCHAR(100) NOT NULL,-- Nombre de la tabla afectada
    consulta TEXT NOT NULL,              -- Query ejecutada
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha y hora de la operación
);

CREATE OR REPLACE FUNCTION registrar_querys()
RETURNS TRIGGER AS $$
DECLARE
    v_cliente_id INTEGER := 1; -- Valor por defecto
BEGIN
    -- Obtener el cliente_id desde la tabla cliente_sesion
    BEGIN
        SELECT cliente_id INTO v_cliente_id
        FROM cliente_sesion
        LIMIT 1;
    EXCEPTION
        WHEN undefined_object THEN
            -- Si ocurre un error, el valor por defecto se mantiene
            v_cliente_id := 1;
    END;

	IF v_cliente_id IS NULL THEN
		v_cliente_id :=1;
	END IF;

    -- Insertar la consulta ejecutada en la tabla de auditoría
    INSERT INTO detalles_querys (cliente_id, operacion, tabla_afectada, consulta)
    VALUES (
        v_cliente_id,                      -- Cliente que ejecutó la consulta
        TG_OP,                             -- Operación (INSERT, UPDATE, DELETE)
        TG_TABLE_NAME,                     -- Tabla afectada
        current_query()                    -- Query ejecutada
    );

    -- Retornar la fila afectada
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_categoria
AFTER INSERT OR UPDATE OR DELETE ON categoria
FOR EACH ROW
EXECUTE FUNCTION registrar_querys();

CREATE TRIGGER trigger_producto
AFTER INSERT OR UPDATE OR DELETE ON producto
FOR EACH ROW
EXECUTE FUNCTION registrar_querys();

CREATE TRIGGER trigger_orden
AFTER INSERT OR UPDATE OR DELETE ON orden
FOR EACH ROW
EXECUTE FUNCTION registrar_querys();

CREATE TRIGGER trigger_detalle_orden
AFTER INSERT OR UPDATE OR DELETE ON detalle_orden
FOR EACH ROW
EXECUTE FUNCTION registrar_querys();

-- Procedimiento almacenado que reporta a los 3 clientes que más operaciones han realizado
CREATE OR REPLACE PROCEDURE reporte_top_usuarios_querys()
LANGUAGE plpgsql
AS $$
BEGIN

	-- Verificar y eliminar la tabla temporal si existe
    DROP TABLE IF EXISTS tmp_reporte_top_usuarios;

    -- Crear la tabla temporal
    CREATE TEMP TABLE IF NOT EXISTS tmp_reporte_top_usuarios (
        nombre_cliente VARCHAR(255),
        consulta_realizada TEXT,
        fecha_consulta TIMESTAMP
    );

    -- Insertar los datos en la tabla temporal
    INSERT INTO tmp_reporte_top_usuarios (nombre_cliente, consulta_realizada, fecha_consulta)
    SELECT 
        c.nombre AS nombre_cliente,
        dq.consulta AS consulta_realizada,
        dq.fecha AS fecha_consulta
    FROM 
        detalles_querys dq
    JOIN 
        cliente c ON dq.cliente_id = c.id_cliente
    WHERE 
        dq.cliente_id NOT IN (1) -- Excluir el cliente con id_cliente = 1
        AND dq.cliente_id IN (
            SELECT cliente_id
            FROM (
                SELECT 
                    dq.cliente_id, 
                    COUNT(*) AS total_operaciones
                FROM 
                    detalles_querys dq
                WHERE 
                    dq.cliente_id NOT IN (1) -- También excluir aquí
                GROUP BY 
                    dq.cliente_id
                ORDER BY 
                    total_operaciones DESC
                LIMIT 3
            ) top_clientes
        )
    ORDER BY 
        c.nombre, 
        dq.fecha DESC;
END;
$$;

-- Procedimiento almacenado para aplicar descuento a una categoría
CREATE OR REPLACE PROCEDURE aplicar_descuento_a_categoria(
    p_id_categoria INT,
	p_descuento NUMERIC(10,4)

)
LANGUAGE plpgsql
AS $$
DECLARE
    v_fecha_ultima_venta DATE;
	v_producto RECORD;
BEGIN
    -- Iterar sobre todos los productos de la categoría
    FOR v_producto IN
        SELECT p.id_producto
        FROM producto p
        WHERE p.id_categoria = p_id_categoria
    LOOP
        -- Obtener la fecha de la última venta de cada producto
        SELECT MAX(o.fecha_orden)
        INTO v_fecha_ultima_venta
        FROM detalle_orden deto
        JOIN orden o ON deto.id_orden = o.id_orden
        WHERE deto.id_producto = v_producto.id_producto;

        -- Si no se ha vendido en los últimos 30 días, aplicar el descuento
        IF v_fecha_ultima_venta IS NULL OR v_fecha_ultima_venta < CURRENT_DATE - INTERVAL '30 days' THEN
            -- Actualizar el precio del producto aplicando el descuento
            UPDATE producto
            SET precio = precio * (1 - p_descuento)
            WHERE id_producto = v_producto.id_producto;
        END IF;
    END LOOP;
END;
$$;

-- Procedimiento almacenado para registrar una orden
CREATE OR REPLACE PROCEDURE registrar_orden(
    p_id_cliente INT,
    lista_detalleOrden JSON,
    p_ubicacion_entrega GEOMETRY
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_id_orden INT;
    v_total DECIMAL(10, 2) := 0;
    detalleOrden JSONB;
    v_id_producto INT;
    v_cantidad INT;
    v_precio_unitario DECIMAL(10, 2);
    v_stock_actual INT;
BEGIN
    -- Se crea la orden
    INSERT INTO orden (id_cliente, total, estado, ubicacion_entrega)
    VALUES (p_id_cliente, 0, 'pagada', p_ubicacion_entrega)
    RETURNING id_orden INTO v_id_orden;
    
    -- Iterar sobre cada detalle de producto del JSON
    FOR detalleOrden IN SELECT * FROM jsonb_array_elements(lista_detalleOrden::jsonb)
    LOOP
	-- Extraer información del detalle
        v_id_producto := (detalleOrden->>'id_producto')::INT;
        v_cantidad := (detalleOrden->>'cantidad')::INT;
        v_precio_unitario := (detalleOrden->>'precio_unitario')::DECIMAL;
        
        -- Buscar el producto y verificar existencia de stock
        SELECT stock INTO v_stock_actual
        FROM producto
        WHERE id_producto = v_id_producto;
        
        -- Verificar que se haya encontrado el producto y que tenga stock suficiente
        -- Si no se puede realizar la compra debido al stock no se realiza el procedimiento
        IF v_stock_actual IS NULL THEN
            RAISE EXCEPTION 'El producto con ID % no existe.', v_id_producto;
        ELSIF v_stock_actual < v_cantidad THEN
            RAISE EXCEPTION 'Stock insuficiente para el producto con ID %.', v_id_producto;
        END IF;
        
        -- Actualizar el stock del producto antes de crear el detalle del orden
        UPDATE producto
        SET stock = stock - v_cantidad
        WHERE id_producto = v_id_producto;
        
        -- Insertar detalle en la tabla detalle_orden
        INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario)
        VALUES (v_id_orden, v_id_producto, v_cantidad, v_precio_unitario);
        
        -- Calcular el total de la orden
        v_total := v_total + (v_cantidad * v_precio_unitario);
    END LOOP;
        
    -- Actualizar el total de la orden que se ha efectuado
    UPDATE orden
    SET total = v_total
    WHERE id_orden = v_id_orden;

END;
$$;
