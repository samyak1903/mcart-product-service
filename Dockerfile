# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compile the code and skip tests to make the build faster for the demo
RUN mvn clean package -DskipTests

# Stage 2: Create the lightweight production image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the compiled .jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port (Change this in each repo to match application.yaml!)
# e.g., 8081 for Product, 8082 for User, 8083 for Search
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]