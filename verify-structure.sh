#!/bin/bash
# Script para verificar la estructura completa de directorios creada

echo "🔍 Verificando estructura de directorios de microservicios..."
echo "=========================================================="

# Función para verificar si existe un directorio
check_dir() {
    if [ -d "$1" ]; then
        echo "✅ $1"
    else
        echo "❌ $1 - FALTA CREAR"
    fi
}

echo ""
echo "📁 CONFIG SERVER:"
check_dir "config-server/src/main/java/uy/bcu/config"
check_dir "config-server/src/main/resources"
check_dir "config-server/src/main/resources/config"

echo ""
echo "📁 SERVICE REGISTRY:"
check_dir "service-registry/src/main/java/uy/bcu/registry"
check_dir "service-registry/src/main/resources"
check_dir "service-registry/src/main/resources/static"

echo ""
echo "📁 API GATEWAY:"
check_dir "api-gateway/src/main/java/uy/bcu/gateway"
check_dir "api-gateway/src/main/java/uy/bcu/gateway/config"
check_dir "api-gateway/src/main/resources"
check_dir "api-gateway/src/main/resources/static"

echo ""
echo "📁 USUARIO SERVICE:"
check_dir "usuario-service/src/main/java/uy/bcu/usuario"
check_dir "usuario-service/src/main/java/uy/bcu/usuario/controller"
check_dir "usuario-service/src/main/java/uy/bcu/usuario/dto"
check_dir "usuario-service/src/main/java/uy/bcu/usuario/service"
check_dir "usuario-service/src/main/java/uy/bcu/usuario/repository"
check_dir "usuario-service/src/main/resources"

echo ""
echo "📁 PRODUCT SERVICE:"
check_dir "product-service/src/main/java/uy/bcu/product"
check_dir "product-service/src/main/java/uy/bcu/product/controller"
check_dir "product-service/src/main/java/uy/bcu/product/dto"
check_dir "product-service/src/main/java/uy/bcu/product/service"
check_dir "product-service/src/main/resources"

echo ""
echo "📁 ORDER SERVICE:"
check_dir "order-service/src/main/java/uy/bcu/order"
check_dir "order-service/src/main/java/uy/bcu/order/controller"
check_dir "order-service/src/main/java/uy/bcu/order/dto"
check_dir "order-service/src/main/java/uy/bcu/order/service"
check_dir "order-service/src/main/resources"

echo ""
echo "📁 NOTIFICATION SERVICE:"
check_dir "notification-service/src/main/java/uy/bcu/notification"
check_dir "notification-service/src/main/java/uy/bcu/notification/controller"
check_dir "notification-service/src/main/java/uy/bcu/notification/dto"
check_dir "notification-service/src/main/java/uy/bcu/notification/service"
check_dir "notification-service/src/main/resources"

echo ""
echo "🎉 Verificación de estructura de directorios completada!"
echo "=========================================================="
