# Use a minimal JDK image for running the app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the locally built JAR into the container
COPY target/flexbuy-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
