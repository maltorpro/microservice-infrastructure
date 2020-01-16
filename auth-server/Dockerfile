FROM openjdk:8-jdk-alpine

EXPOSE 8080

ADD ./build/libs/*.jar app.jar

ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
