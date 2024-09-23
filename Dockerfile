# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/product-management-0.0.1.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Define the command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
