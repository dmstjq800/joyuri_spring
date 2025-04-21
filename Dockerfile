FROM openjdk:21-slim

WORKDIR /app
ADD build/libs/demo-0.0.1-SNAPSHOT.jar  joyuri.jar
ADD application.properties_git application.properties
RUN mkdir images
COPY ./images images/
CMD ["java", "-jar", "joyuri.jar"]
