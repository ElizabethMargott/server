FROM openjdk:18.0
ADD ./docker-spring-boot.jar docker-spring-boot.jar
RUN mkdir -p /uploads
ENTRYPOINT [ "java", "-jar", "docker-spring-boot.jar" ]