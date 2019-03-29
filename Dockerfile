FROM openjdk:8-jdk-alpine
MAINTAINER jferec
EXPOSE 8080
ARG JAR_FILE=target/ride2work-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} ride2work.jar
ENTRYPOINT ["java", "-jar", "/ride2work.jar"]

