# Dockerfile for Config Server
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar config-server.jar

EXPOSE 8888

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8888/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "config-server.jar"]
