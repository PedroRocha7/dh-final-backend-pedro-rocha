FROM openjdk:8-jdk-alpine
RUN apk update && apk add bash
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY wait-for-it.sh .
ENTRYPOINT ["./wait-for-it.sh", "eureka-server:8761", "--", "java", "-jar", "app.jar"]