# Stage 1: Build with Maven
# FROM maven:3.9-amazoncorretto-25-alpine AS builder   # Imágen no oficial (ligero)
# FROM maven:eclipse-temurin AS builder                # Imágen oficial (pesado)
FROM joseph2307/maven:3.9-amazoncorretto-25-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Java
# FROM eclipse-temurin:25-jre-alpine  # Imágen no oficial (ligero)
# FROM openjdk:12-ea-25-jdk-oracle    # Imágen oficial (pesado)
FROM joseph2307/eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


# CONSTRUIR UNA IMÁGEN MEDIANTE UN Dockerfile EN UNA CARPETA
# docker build -t joseph2307/springboot-sqlserver:1.0 -f dockerfile-joseph2307/Dockerfile .

# EJECUTAR Y SUBIR TU IMÁGEN GENERADA A DOCKERHUB
# docker run -d --name springboot-sqlserver -p 8087:8087 joseph2307/springboot-sqlserver:1.0
# docker push joseph2307/springboot-sqlserver-usuario:1.0


# DESCARGAR Y SUBIR MAVEN A TU USUARIO DOCKERHUB
# docker pull maven:3.9-amazoncorretto-25-alpine
# docker tag 2246c1050783 joseph2307/maven:3.9-amazoncorretto-25-alpine
# docker push joseph2307/maven:3.9-amazoncorretto-25-alpine


# DESCARGAR Y SUBIR JDK25 A TU USUARIO DOCKERHUB
# docker pull eclipse-temurin:25-jre-alpine
# docker tag 5fcc27581b23 joseph2307/eclipse-temurin:25-jre-alpine
# docker push joseph2307/eclipse-temurin:25-jre-alpine


# EJECUTAR UN CONTENEDOR SQL SERVER MEDIANTE COMANDO
# docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Admin12345" -p 1433:1433 --name sqlserver -d concaflores/sql-server:2022
