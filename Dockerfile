# syntax=docker/dockerfile:1

FROM amazoncorretto:21

WORKDIR /docker-app

COPY build/libs/nbc-cloud-assignment-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]