FROM openjdk:8-jdk-alpine
VOLUME /tmp

EXPOSE 9090

ADD ./build/libs/*.jar app.jar
ADD ./src/main/resources/cert.p12 cert.p12

ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar


