# Stage 1: Build with Maven
FROM maven:3.9.6-amazoncorretto-21 AS builder
COPY pom.xml .
COPY src ./src
RUN mvn install -DskipTests

# Stage 2: Copy the JAR file from the builder stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /target/bird-park-management-1.2.1.jar app.jar
EXPOSE 1324
ENTRYPOINT ["java","-jar","/app/app.jar"]
