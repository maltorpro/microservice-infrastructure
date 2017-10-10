FROM openjdk:8-jre-alpine

ENV PROFILE docker
EXPOSE 8761

ADD ./build/libs/*.jar app.jar

# Regarding settings of java.security.egd, see http://wiki.apache.org/tomcat/HowTo/FasterStartUp#Entropy_Source
ENTRYPOINT ["java","-Dspring.profiles.active=$PROFILE","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
