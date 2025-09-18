# Dockerfile for Product Service
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar product-service.jar

EXPOSE 8082

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "product-service.jar"]
