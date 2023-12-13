FROM openjdk:18.0

# Copia el archivo JAR de tu aplicación
ADD ./docker-spring-boot.jar docker-spring-boot.jar

# Crea el directorio /uploads dentro de la imagen y copia el contenido de la carpeta uploads de tu proyecto
COPY ./src/uploads /src/uploads

ENTRYPOINT [ "java", "-jar", "docker-spring-boot.jar" ]