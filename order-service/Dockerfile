# Dockerfile for Order Service
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar order-service.jar

EXPOSE 8083

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8083/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "order-service.jar"]
