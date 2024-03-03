#TODO: Alle Datenbanken, Services und Microapps in ein Dockerfile abspeichern, damit die Anwendung allein durch Docker compose startet.
FROM eclipse-temurin:21-jdk-alpine
VOLUME /data
COPY ./target/Backend-1.2.1.jar app.jar
EXPOSE 1234
ENTRYPOINT ["java","-jar","/app.jar"]