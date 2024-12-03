# Use an OpenJDK 23 base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file into the container
COPY target/configapp-0.0.1-SNAPSHOT.jar app.jar

# Copy resources into a config directory within the Docker container
COPY src/main/resources/ /app/config/

# Set the active profile to "docker"
ENV SPRING_PROFILES_ACTIVE=docker

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
