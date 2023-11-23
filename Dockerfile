# Usar una imagen base más pequeña con JDK 17 y Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Establecer un directorio de trabajo
WORKDIR /app

# Excluir archivos con .dockerignore

# Copiar archivos de tu proyecto al directorio de trabajo
COPY . /app

# Ejecutar Maven para construir el proyecto, limpiar caché y archivos temporales
RUN mvn clean package && rm -rf /root/.m2

# Crear una nueva imagen basada en OpenJDK 17 Alpine
FROM openjdk:17-jdk-alpine

# Exponer el puerto que utilizará la aplicación
EXPOSE 3000

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar

# Establecer el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
