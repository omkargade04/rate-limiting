# Stage 1: Build stage
FROM maven:3.9.7-amazoncorretto-21 AS build

# Set the working directory in the build container
WORKDIR /app

# Copy Maven files for dependency resolution
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
COPY mvnw.cmd ./

# Copy application source code
COPY src src

# Build the project and create the executable JAR
RUN mvn clean install -DskipTests

# Stage 2: Run stage
FROM amazoncorretto:21

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build target/*.jar rateLimiter.jar

# Expose port 1224
EXPOSE 1224

# Set the entrypoint command for running the application
ENTRYPOINT ["java", "-jar", "rateLimiter.jar"]