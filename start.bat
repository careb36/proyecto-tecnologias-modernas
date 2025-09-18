@echo off
REM Script de inicio para Windows
REM Equivalente al start.sh pero para sistemas Windows

echo 🚀 Iniciando Proyecto de Tecnologias...
echo ======================================

REM Verificar Docker
docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker no esta instalado. Por favor instala Docker Desktop.
    pause
    exit /b 1
)

REM Verificar Docker Compose
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker Compose no esta instalado.
    pause
    exit /b 1
)

echo ✅ Prerrequisitos verificados

REM Crear archivo .env si no existe
if not exist .env (
    echo 📁 Creando archivo .env desde .env.example...
    copy .env.example .env
    echo ⚠️  Por favor, edita el archivo .env con tus configuraciones especificas
)

REM Crear directorios necesarios
echo 📁 Creando directorios necesarios...
if not exist logs mkdir logs
if not exist backups mkdir backups
if not exist data\grafana mkdir data\grafana
if not exist data\loki mkdir data\loki
if not exist data\prometheus mkdir data\prometheus

REM Construir aplicacion si Maven esta disponible
mvn --version >nul 2>&1
if not errorlevel 1 (
    echo 🔨 Construyendo aplicacion Java...
    mvn clean package -DskipTests
    if not errorlevel 1 (
        echo ✅ Aplicacion construida exitosamente
    ) else (
        echo ⚠️  Error al construir la aplicacion. Continuando...
    )
)

REM Iniciar servicios de infraestructura
echo 🐳 Iniciando servicios de infraestructura...
docker-compose up -d oracle postgres loki prometheus

echo ⏳ Esperando a que Oracle Database este listo...
echo    Esto puede tomar varios minutos la primera vez...

REM Esperar a Oracle (simplificado para Windows)
timeout /t 60 /nobreak >nul

echo 🐳 Iniciando servicios restantes...
docker-compose up -d

echo ⏳ Esperando a que todos los servicios esten listos...
timeout /t 30 /nobreak >nul

echo 🔍 Verificando estado de servicios...
docker-compose ps

echo.
echo 🎉 ¡Proyecto iniciado exitosamente!
echo ==================================
echo.
echo 📱 Servicios disponibles:
echo    • Aplicacion Java:    http://localhost:8080
echo    • Swagger UI:         http://localhost:8080/swagger-ui.html
echo    • Grafana:           http://localhost:3000 (admin/admin)
echo    • SonarQube:         http://localhost:9000 (admin/admin)
echo    • Portainer:         http://localhost:9001
echo    • Oracle EM Express: http://localhost:5500/em (system/oracle)
echo.
echo 📚 Documentacion disponible en la carpeta docs/
echo 🔧 Para ver logs: docker-compose logs -f [servicio]
echo 🛑 Para parar: docker-compose down
echo.
echo 🎓 ¡Feliz aprendizaje con las tecnologias!
pause
