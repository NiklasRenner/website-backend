FROM openjdk:8

VOLUME /tmp
ADD website-backend.war app.war

EXPOSE 1337
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]