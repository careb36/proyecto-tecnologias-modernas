# Dockerfile for API Gateway
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar api-gateway.jar

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
