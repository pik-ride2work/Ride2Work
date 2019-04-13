FROM openjdk:8-jdk-alpine
MAINTAINER jferec
EXPOSE 8080
ARG JAR_FILE=backend/target/backend-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} ride2work.jar
CMD java -jar /ride2work.jar

FROM node:8.11.2-alpine
WORKDIR /usr/src/app
COPY frontend/src/main/web/package*.json ./
RUN npm install
RUN npm config set unsafe-perm true
RUN npm install -g @angular/cli@1.7.1
COPY . .
WORKDIR frontend/src/main/web
CMD npm start


