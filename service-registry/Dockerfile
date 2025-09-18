# Dockerfile for Service Registry
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar service-registry.jar

EXPOSE 8761

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8761/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "service-registry.jar"]
