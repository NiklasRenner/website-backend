FROM openjdk:8

VOLUME /tmp
ADD target/website-backend.war app.jar

EXPOSE 1337
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]