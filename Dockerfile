# ---- Build Stage ----
FROM gradle:8.4-jdk21 AS builder

WORKDIR /app
COPY . .

# Use this to build the executable Spring Boot JAR
RUN ./gradlew bootJar

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app
COPY --from=builder /app/build/libs/websocket-server-0.0.1.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
