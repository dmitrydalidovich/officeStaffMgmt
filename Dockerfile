FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle ./gradle
RUN ./gradlew dependencies
COPY src ./src
RUN ./gradlew clean build -x test

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

