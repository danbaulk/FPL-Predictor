# Stage 1: Build the JAR file
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Use a compatible Java runtime for ARM64
FROM eclipse-temurin:17-jdk 
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENV MANAGEMENT_METRICS_ENABLE_PROCESS=false
ENV MANAGEMENT_METRICS_ENABLE_SYSTEM=false
CMD ["java", "--add-opens", "java.base/jdk.internal.platform=ALL-UNNAMED", "-XX:-UseContainerSupport", "-jar", "app.jar"]
