FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} imdb-rest-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","imdb-rest-api.jar"]