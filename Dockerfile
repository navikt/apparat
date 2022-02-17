FROM gradle:7.3.3-jdk17-alpine AS buildToJar
COPY . .

FROM eclipse-temurin:17-jdk-alpine

RUN apk update && apk add libc6-compat

COPY --from=buildToJar /home/gradle/app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
