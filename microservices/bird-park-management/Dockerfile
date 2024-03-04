# Use an official Maven image as the base image
FROM maven:3.9.6-amazoncorretto-21 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn install

FROM eclipse-temurin:21-jdk-alpine
COPY ./target/bird-park-management-1.2.1.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]