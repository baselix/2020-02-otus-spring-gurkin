FROM openjdk:11-jre-slim

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY /target/journal-1.0.0-SNAPSHOT.jar $PROJECT_DIR
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/project/journal-1.0.0-SNAPSHOT.jar"]
