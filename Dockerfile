FROM openjdk:8-jdk-alpine
MAINTAINER jferec
EXPOSE 8080
ARG JAR_FILE=backend/target/backend-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} ride2work.jar
ENTRYPOINT ["java", "-jar", "/ride2work.jar"]
WORKDIR frontend/src/main/web
CMD npm install
CMD ["ng","serve","--host", "0.0.0.0"]

