# ─────────────────────────────────────────────────────────────────────────────
# Dockerfile-grupo — Backend Spring Boot (Java 25)
# Imagen de grupo: concaflores/proyectobackend:latest
# ─────────────────────────────────────────────────────────────────────────────

# Stage 1: Build con Maven + Amazon Corretto 25
FROM maven:3.9-amazoncorretto-25-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# Stage 2: Run con Amazon Corretto 25 (ligero)
FROM amazoncorretto:25-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]


# ─────────────────────────────────────────────────────────────────────────────
# CONSTRUIR Y SUBIR LA IMAGEN (ejecutar desde la raíz del proyecto BE)
#
# docker build -t concaflores/proyectobackend:latest -f Dockerfile-grupo/Dockerfile .
# docker push concaflores/proyectobackend:latest
# ─────────────────────────────────────────────────────────────────────────────
