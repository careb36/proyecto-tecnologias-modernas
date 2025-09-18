-- Script de inicialización para Oracle Database
-- Este script se ejecuta automáticamente cuando se inicia el contenedor

-- Crear usuario de aplicación
CREATE USER appuser IDENTIFIED BY "AppPassword123!";

-- Otorgar permisos necesarios
GRANT CONNECT, RESOURCE TO appuser;
GRANT CREATE SESSION TO appuser;
GRANT CREATE TABLE TO appuser;
GRANT CREATE SEQUENCE TO appuser;
GRANT CREATE VIEW TO appuser;
GRANT CREATE PROCEDURE TO appuser;
GRANT CREATE TRIGGER TO appuser;

-- Asignar cuota ilimitada en el tablespace USERS
ALTER USER appuser QUOTA UNLIMITED ON USERS;

-- Crear tablas de ejemplo
CREATE TABLE appuser.usuarios (
    id NUMBER(10) PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    email VARCHAR2(255) UNIQUE NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

CREATE TABLE appuser.productos (
    id NUMBER(10) PRIMARY KEY,
    nombre VARCHAR2(200) NOT NULL,
    descripcion CLOB,
    precio NUMBER(10,2) NOT NULL,
    stock NUMBER(10) DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appuser.pedidos (
    id NUMBER(10) PRIMARY KEY,
    usuario_id NUMBER(10) NOT NULL,
    total NUMBER(12,2) NOT NULL,
    estado VARCHAR2(50) DEFAULT 'PENDIENTE',
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pedidos_usuario FOREIGN KEY (usuario_id) REFERENCES appuser.usuarios(id)
);

-- Crear secuencias para autoincrementar
CREATE SEQUENCE appuser.seq_usuarios START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE appuser.seq_productos START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE appuser.seq_pedidos START WITH 1 INCREMENT BY 1;

-- Crear triggers para autoincrementar
CREATE OR REPLACE TRIGGER appuser.tr_usuarios_id
    BEFORE INSERT ON appuser.usuarios
    FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT appuser.seq_usuarios.NEXTVAL INTO :NEW.id FROM DUAL;
    END IF;
END;

CREATE OR REPLACE TRIGGER appuser.tr_productos_id
    BEFORE INSERT ON appuser.productos
    FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT appuser.seq_productos.NEXTVAL INTO :NEW.id FROM DUAL;
    END IF;
END;

CREATE OR REPLACE TRIGGER appuser.tr_pedidos_id
    BEFORE INSERT ON appuser.pedidos
    FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT appuser.seq_pedidos.NEXTVAL INTO :NEW.id FROM DUAL;
    END IF;
END;

-- Insertar datos de ejemplo
INSERT INTO appuser.usuarios (nombre, email) VALUES ('Juan Pérez', 'juan.perez@ejemplo.com');
INSERT INTO appuser.usuarios (nombre, email) VALUES ('María García', 'maria.garcia@ejemplo.com');
INSERT INTO appuser.usuarios (nombre, email) VALUES ('Carlos López', 'carlos.lopez@ejemplo.com');

INSERT INTO appuser.productos (nombre, descripcion, precio, stock)
VALUES ('Laptop Dell', 'Laptop Dell Inspiron 15 3000', 899.99, 50);

INSERT INTO appuser.productos (nombre, descripcion, precio, stock)
VALUES ('Mouse Logitech', 'Mouse inalámbrico Logitech M705', 49.99, 100);

INSERT INTO appuser.productos (nombre, descripcion, precio, stock)
VALUES ('Teclado Mecánico', 'Teclado mecánico RGB', 129.99, 25);

INSERT INTO appuser.pedidos (usuario_id, total, estado) VALUES (1, 949.98, 'COMPLETADO');
INSERT INTO appuser.pedidos (usuario_id, total, estado) VALUES (2, 179.98, 'PENDIENTE');

-- Crear vista para reportes
CREATE OR REPLACE VIEW appuser.v_pedidos_detalle AS
SELECT
    p.id,
    u.nombre as usuario,
    u.email,
    p.total,
    p.estado,
    p.fecha_pedido
FROM appuser.pedidos p
JOIN appuser.usuarios u ON p.usuario_id = u.id;

-- Crear procedimiento almacenado de ejemplo
CREATE OR REPLACE PROCEDURE appuser.sp_actualizar_stock(
    p_producto_id IN NUMBER,
    p_cantidad IN NUMBER
) AS
BEGIN
    UPDATE appuser.productos
    SET stock = stock - p_cantidad
    WHERE id = p_producto_id;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Producto no encontrado');
    END IF;

    COMMIT;
END sp_actualizar_stock;

-- Confirmar cambios
COMMIT;

-- Mostrar mensaje de finalización
SELECT 'Base de datos inicializada correctamente' AS mensaje FROM DUAL;
