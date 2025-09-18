#!/bin/bash

# Script de inicio para el proyecto de tecnologías
# Este script automatiza el proceso de inicialización completo

echo "🚀 Iniciando Proyecto de Tecnologías..."
echo "======================================"

# Función para verificar si un comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Verificar prerrequisitos
echo "📋 Verificando prerrequisitos..."

if ! command_exists docker; then
    echo "❌ Docker no está instalado. Por favor instala Docker Desktop."
    exit 1
fi

if ! command_exists docker-compose; then
    echo "❌ Docker Compose no está instalado."
    exit 1
fi

if ! command_exists mvn; then
    echo "⚠️  Maven no está instalado. Algunas funcionalidades estarán limitadas."
fi

echo "✅ Prerrequisitos verificados"

# Crear archivo .env si no existe
if [ ! -f .env ]; then
    echo "📁 Creando archivo .env desde .env.example..."
    cp .env.example .env
    echo "⚠️  Por favor, edita el archivo .env con tus configuraciones específicas"
fi

# Crear directorios necesarios
echo "📁 Creando directorios necesarios..."
mkdir -p logs
mkdir -p backups
mkdir -p data/grafana
mkdir -p data/loki
mkdir -p data/prometheus

# Construir la aplicación Java si Maven está disponible
if command_exists mvn; then
    echo "🔨 Construyendo aplicación Java..."
    mvn clean package -DskipTests
    if [ $? -eq 0 ]; then
        echo "✅ Aplicación construida exitosamente"
    else
        echo "⚠️  Error al construir la aplicación. Continuando con imagen base..."
    fi
fi

# Iniciar servicios de infraestructura primero
echo "🐳 Iniciando servicios de infraestructura..."
docker-compose up -d oracle postgres loki prometheus

echo "⏳ Esperando a que Oracle Database esté listo..."
echo "   Esto puede tomar varios minutos la primera vez..."

# Esperar a que Oracle esté listo
until docker-compose exec -T oracle sqlplus -L system/oracle@xe <<< "SELECT 1 FROM DUAL;" >/dev/null 2>&1; do
    echo "   Oracle aún no está listo, esperando..."
    sleep 30
done

echo "✅ Oracle Database está listo"

# Iniciar resto de servicios
echo "🐳 Iniciando servicios restantes..."
docker-compose up -d

# Esperar a que todos los servicios estén listos
echo "⏳ Esperando a que todos los servicios estén listos..."
sleep 30

# Verificar estado de servicios
echo "🔍 Verificando estado de servicios..."
docker-compose ps

echo ""
echo "🎉 ¡Proyecto iniciado exitosamente!"
echo "=================================="
echo ""
echo "📱 Servicios disponibles:"
echo "   • Aplicación Java:    http://localhost:8080"
echo "   • Swagger UI:         http://localhost:8080/swagger-ui.html"
echo "   • Grafana:           http://localhost:3000 (admin/admin)"
echo "   • SonarQube:         http://localhost:9000 (admin/admin)"
echo "   • Portainer:         http://localhost:9001"
echo "   • Oracle EM Express: http://localhost:5500/em (system/oracle)"
echo ""
echo "📚 Documentación disponible en la carpeta docs/"
echo "🔧 Para ver logs: docker-compose logs -f [servicio]"
echo "🛑 Para parar: docker-compose down"
echo ""
echo "🎓 ¡Feliz aprendizaje con las tecnologías!"
