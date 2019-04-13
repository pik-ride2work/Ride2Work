FROM node:latest

WORKDIR /usr/src/app

COPY backend/target/backend-1.0-SNAPSHOT.jar ./ride2work.jar

COPY frontend/src/main/web/package*.json ./

COPY . .

WORKDIR /usr/src/app/frontend/src/main/web

RUN npm cache clean --force && npm install

RUN npm rebuild node-sass

RUN npm install -g @angular/cli

EXPOSE 4200

ENV PORT 4200

RUN apt update

RUN apt install -y default-jdk

CMD java -jar /usr/src/app/ride2work.jar & ng serve --host 0.0.0.0


