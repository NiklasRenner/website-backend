FROM openjdk:13-jdk

VOLUME /tmp
ADD website-backend.war app.jar

EXPOSE 1337
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]