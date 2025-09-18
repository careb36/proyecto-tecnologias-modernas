}
```

## Performance y Optimización

### 1. Índices
```sql
-- Crear índices para mejorar performance
CREATE INDEX idx_usuarios_email ON appuser.usuarios(email);
CREATE INDEX idx_usuarios_fecha ON appuser.usuarios(fecha_creacion);
CREATE INDEX idx_pedidos_usuario ON appuser.pedidos(usuario_id);
CREATE INDEX idx_pedidos_fecha ON appuser.pedidos(fecha_pedido);

-- Ver índices existentes
SELECT index_name, table_name, column_name 
FROM user_ind_columns 
WHERE table_name IN ('USUARIOS', 'PRODUCTOS', 'PEDIDOS');
```

### 2. Particionamiento (Oracle XE limitado)
```sql
-- Ejemplo conceptual (requiere Oracle Standard+)
CREATE TABLE pedidos_particionados (
    id NUMBER(10),
    fecha_pedido DATE,
    -- otros campos
) PARTITION BY RANGE (fecha_pedido) (
    PARTITION p_2023 VALUES LESS THAN (DATE '2024-01-01'),
    PARTITION p_2024 VALUES LESS THAN (DATE '2025-01-01')
);
```

### 3. Connection Pool Optimization
```yaml
# application.yml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
      validation-timeout: 5000
```

## Backup y Recovery

### 1. Backup Lógico con Data Pump
```bash
# Dentro del contenedor Oracle
docker exec -it <oracle-container> bash

# Export schema
expdp system/oracle@xe SCHEMAS=appuser DIRECTORY=DATA_PUMP_DIR DUMPFILE=appuser.dmp

# Import schema
impdp system/oracle@xe SCHEMAS=appuser DIRECTORY=DATA_PUMP_DIR DUMPFILE=appuser.dmp
```

### 2. Backup del Volumen Docker
```bash
# Parar Oracle container
docker-compose stop oracle

# Crear backup del volumen
docker run --rm -v oracle_data:/data -v $(pwd)/backups:/backup alpine tar czf /backup/oracle-backup.tar.gz -C /data .

# Restaurar backup
docker run --rm -v oracle_data:/data -v $(pwd)/backups:/backup alpine tar xzf /backup/oracle-backup.tar.gz -C /data
```

### 3. Backup Automático con Script
```bash
#!/bin/bash
# backup-oracle.sh
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="./backups"

echo "Starting Oracle backup at $(date)"

# Crear directorio de backup
mkdir -p $BACKUP_DIR

# Export con Data Pump
docker exec <oracle-container> expdp system/oracle@xe \
  SCHEMAS=appuser \
  DIRECTORY=DATA_PUMP_DIR \
  DUMPFILE=appuser_$DATE.dmp

# Copiar dump file
docker cp <oracle-container>:/opt/oracle/admin/XE/dpdump/appuser_$DATE.dmp $BACKUP_DIR/

echo "Oracle backup completed at $(date)"
```

## Monitoring y Troubleshooting

### 1. Métricas Básicas
```sql
-- Verificar estado de la instancia
SELECT status FROM v$instance;

-- Ver sesiones activas
SELECT username, machine, program, status 
FROM v$session 
WHERE username IS NOT NULL;

-- Espacio en tablespaces
SELECT 
    tablespace_name,
    ROUND(bytes/1024/1024, 2) as MB_TOTAL,
    ROUND(free_space/1024/1024, 2) as MB_FREE,
    ROUND((free_space/bytes)*100, 2) as PCT_FREE
FROM (
    SELECT 
        tablespace_name,
        SUM(bytes) as bytes,
        SUM(CASE WHEN autoextensible = 'YES' THEN maxbytes ELSE bytes END) as max_bytes
    FROM dba_data_files
    GROUP BY tablespace_name
) df,
(
    SELECT 
        tablespace_name,
        SUM(bytes) as free_space
    FROM dba_free_space
    GROUP BY tablespace_name
) fs
WHERE df.tablespace_name = fs.tablespace_name;
```

### 2. Log Files
```bash
# Ver alert log
docker exec <oracle-container> tail -f /opt/oracle/diag/rdbms/xe/XE/trace/alert_XE.log

# Ver listener log
docker exec <oracle-container> tail -f /opt/oracle/diag/tnslsnr/*/listener/trace/listener.log
```

### 3. Performance Monitoring
```sql
-- Top SQL statements
SELECT sql_text, executions, elapsed_time, cpu_time
FROM v$sql
WHERE executions > 0
ORDER BY elapsed_time DESC
FETCH FIRST 10 ROWS ONLY;

-- Wait events
SELECT event, total_waits, time_waited
FROM v$system_event
WHERE wait_class != 'Idle'
ORDER BY time_waited DESC
FETCH FIRST 10 ROWS ONLY;
```

## Seguridad

### 1. Usuarios y Roles
```sql
-- Crear rol personalizado
CREATE ROLE app_readonly;
GRANT SELECT ON appuser.usuarios TO app_readonly;
GRANT SELECT ON appuser.productos TO app_readonly;

-- Crear usuario de solo lectura
CREATE USER readonly_user IDENTIFIED BY "ReadOnly123!";
GRANT CONNECT TO readonly_user;
GRANT app_readonly TO readonly_user;

-- Auditoria de conexiones
AUDIT CREATE SESSION;
```

### 2. Encriptación de Datos
```sql
-- Ejemplo de encriptación a nivel de aplicación
CREATE OR REPLACE FUNCTION encrypt_email(p_email VARCHAR2) 
RETURN VARCHAR2 IS
BEGIN
    -- En producción, usar DBMS_CRYPTO
    RETURN UTL_RAW.CAST_TO_VARCHAR2(UTL_ENCODE.BASE64_ENCODE(UTL_RAW.CAST_TO_RAW(p_email)));
END;
```

### 3. Network Security
```bash
# Configurar listener para IPs específicas
# En $ORACLE_HOME/network/admin/listener.ora
LISTENER =
  (DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1521))
  )

# Configurar válid node checking
VALID_NODE_CHECKING_REGISTRATION_LISTENER = ON
```

## Troubleshooting Común

### 1. Container No Inicia
```bash
# Ver logs detallados
docker logs <oracle-container> --details

# Verificar espacio en disco
df -h

# Verificar memoria disponible
free -h
```

### 2. No Se Puede Conectar
```bash
# Verificar que el listener esté corriendo
docker exec <oracle-container> lsnrctl status
# Oracle Database - Guía de Aprendizaje
# Verificar puerto
netstat -an | grep 1521

# Test de conectividad
docker exec <oracle-container> tnsping xe
```

### 3. Performance Issues
```sql
-- Verificar tablespace lleno
SELECT tablespace_name, 
       ROUND(used_percent, 2) as used_percent
FROM dba_tablespace_usage_metrics;

-- Verificar locks
SELECT blocking_session, sid, serial#, wait_class, event
FROM v$session
WHERE blocking_session IS NOT NULL;

-- Estadísticas de tablas desactualizadas
SELECT table_name, last_analyzed
FROM user_tables
WHERE last_analyzed < SYSDATE - 7;
```

## Migración y Upgrades

### 1. Migración desde Otras Bases de Datos
```sql
-- Ejemplo de migración desde MySQL
-- 1. Exportar datos de MySQL
-- 2. Adaptar tipos de datos
-- 3. Usar SQL*Loader o Data Pump

-- Mapeo de tipos comunes
-- MySQL INT -> Oracle NUMBER(10)
-- MySQL VARCHAR(255) -> Oracle VARCHAR2(255)
-- MySQL TEXT -> Oracle CLOB
-- MySQL DATETIME -> Oracle TIMESTAMP
```

### 2. Upgrade de Versión Oracle
```bash
# Backup antes del upgrade
docker exec <oracle-container> expdp system/oracle@xe FULL=y DUMPFILE=full_backup.dmp

# Upgrade container (cambiar tag en docker-compose)
# Verificar compatibilidad de aplicación
```

## Best Practices

### 1. Desarrollo
- Usar bind variables para evitar SQL injection
- Implementar connection pooling
- Usar transacciones apropiadamente
- Optimizar queries con EXPLAIN PLAN

### 2. Operaciones
- Backup regulares automatizados
- Monitoring de performance
- Mantenimiento de estadísticas
- Cleanup de objetos temporales

### 3. Seguridad
- Principio de menor privilegio
- Encriptación de datos sensibles
- Auditoría de actividades críticas
- Updates de seguridad regulares

## Recursos Adicionales

- [Oracle Database Documentation](https://docs.oracle.com/en/database/)
- [Oracle Express Edition](https://www.oracle.com/database/technologies/appdev/xe.html)
- [Oracle Docker Images](https://github.com/oracle/docker-images)
- [Oracle SQL Developer](https://www.oracle.com/tools/downloads/sqldev-downloads.html)

## Próximos Pasos

1. Familiarizarse con SQL*Plus y Enterprise Manager
2. Practicar con PL/SQL para lógica compleja
3. Explorar Oracle Advanced Features
4. Implementar estrategias de backup robustas
5. Considerar Oracle Cloud para producción

## ¿Qué es Oracle Database?

Oracle Database es un sistema de gestión de base de datos relacional (RDBMS) empresarial que proporciona alta disponibilidad, escalabilidad y seguridad para aplicaciones críticas.

## Conceptos Clave

### 1. Instance vs Database
- **Database**: Conjunto de archivos físicos que almacenan datos
- **Instance**: Procesos de memoria y background que gestionan la database

### 2. Tablespaces
Contenedores lógicos que agrupan objetos de base de datos:
- **SYSTEM**: Objetos del sistema Oracle
- **USERS**: Objetos de usuarios por defecto
- **TEMP**: Espacio temporal para operaciones
- **UNDO**: Información de rollback

### 3. Schemas
Colección de objetos de base de datos (tablas, vistas, procedimientos) pertenecientes a un usuario específico.

### 4. Oracle Express Edition (XE)
Versión gratuita de Oracle Database con limitaciones:
- Máximo 2 CPU cores
- Máximo 2GB RAM
- Máximo 12GB de datos de usuario

## Configuración en el Proyecto

### 1. Contenedor Oracle XE
```yaml
oracle:
  image: container-registry.oracle.com/database/express:21.3.0-xe
  ports:
    - "1521:1521"  # Database listener
    - "5500:5500"  # Enterprise Manager Express
  environment:
    - ORACLE_PWD=oracle
    - ORACLE_CHARACTERSET=AL32UTF8
  volumes:
    - oracle_data:/opt/oracle/oradata
    - ./oracle/init:/docker-entrypoint-initdb.d
```

### 2. Configuración de Red
- **Puerto 1521**: Listener de la base de datos
- **Puerto 5500**: Oracle Enterprise Manager Express (Web UI)
- **SID**: xe (Oracle Express Edition)
- **Service Name**: xe

### 3. Credenciales por Defecto
- **Usuario**: system
- **Password**: oracle
- **Usuario App**: appuser
- **Password App**: AppPassword123!

## Conexión a Oracle

### 1. Desde la Aplicación Java
```properties
# application.yml
spring:
  datasource:
    url: jdbc:oracle:thin:@localhost:1521:xe
    username: system
    password: oracle
    driver-class-name: oracle.jdbc.OracleDriver
```

### 2. Usando SQL*Plus
```bash
# Desde el contenedor
docker exec -it <oracle-container> sqlplus system/oracle@xe

# Desde host (requiere Oracle Client)
sqlplus system/oracle@localhost:1521/xe
```

### 3. Usando Oracle SQL Developer
- Host: localhost
- Port: 1521
- SID: xe
- Username: system
- Password: oracle

### 4. Enterprise Manager Express
- URL: http://localhost:5500/em
- Usuario: system
- Password: oracle

## Estructura de Datos del Proyecto

### 1. Schema de Usuario
El script `oracle/init/01-init.sql` crea:

```sql
-- Usuario de aplicación
CREATE USER appuser IDENTIFIED BY "AppPassword123!";

-- Permisos básicos
GRANT CONNECT, RESOURCE TO appuser;
GRANT CREATE SESSION TO appuser;
GRANT CREATE TABLE TO appuser;
GRANT CREATE SEQUENCE TO appuser;
GRANT CREATE VIEW TO appuser;
```

### 2. Tablas Principales
```sql
-- Tabla de usuarios
CREATE TABLE appuser.usuarios (
    id NUMBER(10) PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    email VARCHAR2(255) UNIQUE NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

-- Tabla de productos
CREATE TABLE appuser.productos (
    id NUMBER(10) PRIMARY KEY,
    nombre VARCHAR2(200) NOT NULL,
    descripcion CLOB,
    precio NUMBER(10,2) NOT NULL,
    stock NUMBER(10) DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de pedidos
CREATE TABLE appuser.pedidos (
    id NUMBER(10) PRIMARY KEY,
    usuario_id NUMBER(10) NOT NULL,
    total NUMBER(12,2) NOT NULL,
    estado VARCHAR2(50) DEFAULT 'PENDIENTE',
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pedidos_usuario FOREIGN KEY (usuario_id) REFERENCES appuser.usuarios(id)
);
```

### 3. Secuencias para Auto-increment
```sql
-- Secuencias
CREATE SEQUENCE appuser.seq_usuarios START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE appuser.seq_productos START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE appuser.seq_pedidos START WITH 1 INCREMENT BY 1;

-- Triggers para auto-incrementar
CREATE OR REPLACE TRIGGER appuser.tr_usuarios_id
    BEFORE INSERT ON appuser.usuarios
    FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT appuser.seq_usuarios.NEXTVAL INTO :NEW.id FROM DUAL;
    END IF;
END;
```

## Ejercicios Prácticos

### Ejercicio 1: Conexión y Exploración
```bash
# 1. Iniciar Oracle container
docker-compose up -d oracle

# 2. Esperar a que inicie (puede tomar varios minutos)
docker logs -f <oracle-container>

# 3. Conectar con SQL*Plus
docker exec -it <oracle-container> sqlplus system/oracle@xe

# 4. Explorar la estructura
SELECT tablespace_name FROM dba_tablespaces;
SELECT username FROM dba_users WHERE username = 'APPUSER';
```

### Ejercicio 2: Consultas Básicas
```sql
-- Conectar como appuser
CONNECT appuser/AppPassword123!@xe;

-- Ver datos de ejemplo
SELECT * FROM usuarios;
SELECT * FROM productos;
SELECT * FROM pedidos;

-- Consulta con JOIN
SELECT 
    u.nombre,
    u.email,
    p.total,
    p.estado,
    p.fecha_pedido
FROM usuarios u
JOIN pedidos p ON u.id = p.usuario_id;
```

### Ejercicio 3: Operaciones CRUD
```sql
-- CREATE: Insertar nuevo usuario
INSERT INTO usuarios (nombre, email) 
VALUES ('Ana Martínez', 'ana.martinez@ejemplo.com');

-- READ: Buscar usuarios
SELECT * FROM usuarios WHERE nombre LIKE '%Ana%';

-- UPDATE: Actualizar usuario
UPDATE usuarios 
SET nombre = 'Ana María Martínez' 
WHERE email = 'ana.martinez@ejemplo.com';

-- DELETE: Desactivar usuario (soft delete)
UPDATE usuarios 
SET activo = 0 
WHERE email = 'ana.martinez@ejemplo.com';

COMMIT;
```

### Ejercicio 4: Procedimientos Almacenados
```sql
-- Usar el procedimiento incluido
EXECUTE sp_actualizar_stock(1, 5);

-- Crear nuevo procedimiento
CREATE OR REPLACE PROCEDURE sp_crear_pedido(
    p_usuario_id IN NUMBER,
    p_total IN NUMBER,
    p_pedido_id OUT NUMBER
) AS
BEGIN
    INSERT INTO pedidos (usuario_id, total, estado)
    VALUES (p_usuario_id, p_total, 'PENDIENTE')
    RETURNING id INTO p_pedido_id;
    
    COMMIT;
END sp_crear_pedido;

-- Usar el procedimiento
DECLARE
    v_pedido_id NUMBER;
BEGIN
    sp_crear_pedido(1, 299.99, v_pedido_id);
    DBMS_OUTPUT.PUT_LINE('Pedido creado con ID: ' || v_pedido_id);
END;
```

## JPA/Hibernate con Oracle

### 1. Configuración en Spring Boot
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.Oracle12cDialect
        format_sql: true
```

### 2. Entity con Oracle-specific Features
```java
@Entity
@Table(name = "usuarios", schema = "appuser")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuarios")
    @SequenceGenerator(
        name = "seq_usuarios", 
        sequenceName = "appuser.seq_usuarios", 
        allocationSize = 1
    )
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    // Usar Oracle TIMESTAMP
    @Column(name = "fecha_creacion")
    @CreationTimestamp
    private LocalDateTime fechaCreacion;
    
    // Boolean mapping para Oracle (1/0)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "activo")
    private Boolean activo = true;
}
```

### 3. Queries Nativas Específicas de Oracle
```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Query nativa con funciones Oracle
    @Query(value = """
        SELECT * FROM appuser.usuarios 
        WHERE UPPER(nombre) LIKE UPPER('%' || ?1 || '%')
        AND ROWNUM <= 10
        ORDER BY fecha_creacion DESC
        """, nativeQuery = true)
    List<Usuario> buscarPorNombreOracle(String nombre);
    
    // Usando funciones de fecha Oracle
    @Query(value = """
        SELECT COUNT(*) FROM appuser.usuarios 
        WHERE fecha_creacion >= TRUNC(SYSDATE) - 30
        """, nativeQuery = true)
    Long contarUsuariosUltimos30Dias();

