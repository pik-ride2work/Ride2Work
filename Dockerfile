FROM node:latest

WORKDIR /usr/src/app

ARG JAR_FILE=backend/target/backend-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} ride2work.jar

COPY frontend/src/main/web/package*.json ./

COPY . .

WORKDIR /usr/src/app/frontend/src/main/web

RUN npm cache clean --force && npm install

RUN npm rebuild node-sass

RUN npm install -g @angular/cli

EXPOSE 4200

ENV PORT 4200

COPY wrapper_runner.sh /

CMD /wrapper_runner.sh


