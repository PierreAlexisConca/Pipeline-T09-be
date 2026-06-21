# Stage 1: Build con Maven + Amazon Corretto 25
FROM maven:3.9-amazoncorretto-25-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# Stage 2: Run con Java 25
FROM amazoncorretto:25-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]


# 1. Construir la imagen desde este Dockerfile
# docker build -t joseph2307/joseph-backend:1.0 -f Dockerfile-joseph/Dockerfile .

# 2. Subir la imagen a DockerHub
# docker push joseph2307/joseph-backend:1.0