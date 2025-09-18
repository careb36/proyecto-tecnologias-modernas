# Dockerfile for Notification Service
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar notification-service.jar

EXPOSE 8084

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8084/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "notification-service.jar"]
