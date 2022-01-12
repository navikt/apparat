FROM gradle:7.3.3-jdk17-alpine AS buildToJar
COPY . .
RUN gradle app:shadowJar --no-daemon

# Hentet fra "gradle:7.3.3-jdk17-alpine"
FROM eclipse-temurin:17-jdk-alpine
COPY --from=buildToJar /home/gradle/app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
