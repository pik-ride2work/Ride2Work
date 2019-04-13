FROM openjdk:8-jdk-alpine
MAINTAINER jferec
EXPOSE 8080
COPY wrapper_runner.sh /wrapper_runner.sh
COPY backend/target/backend-1.0-SNAPSHOT.jar /backend.jar
FROM node:8.11.2-alpine
WORKDIR /usr/src/app
COPY frontend/src/main/web/package*.json ./
RUN npm install
RUN npm config set unsafe-perm true
RUN npm install -g @angular/cli@1.7.1
COPY . .x
CMD ./wrapper_runner.sh


