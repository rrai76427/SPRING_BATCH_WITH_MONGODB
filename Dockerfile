# Use Java 21 as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy the JAR file to /usr/src/app/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Copy the properties file to /usr/src/app/
COPY application.yml application.yml

# # Set the entry point to run the JAR file with the properties file
# ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=applicationdatabase.properties"]
