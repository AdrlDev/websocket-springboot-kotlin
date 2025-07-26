# Use OpenJDK base image
FROM eclipse-temurin:21-jdk AS builder

# Set working directory
WORKDIR /app

# Copy Gradle files separately to optimize Docker cache
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Download dependencies (optional but speeds up builds)
RUN ./gradlew dependencies || true

# Copy the rest of your code
COPY . .

# Build the JAR
RUN ./gradlew bootJar

# ---- Runtime image ----
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
