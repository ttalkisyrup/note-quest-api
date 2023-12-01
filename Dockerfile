FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=/build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} note-quest-api.jar
ENTRYPOINT ["java","-jar","/note-quest-api.jar"]
