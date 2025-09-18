# Dockerfile for Usuario Service
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar usuario-service.jar

EXPOSE 8081

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "usuario-service.jar"]
