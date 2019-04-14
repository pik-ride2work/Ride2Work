FROM node:latest

WORKDIR /usr/src/app

COPY frontend/src/main/web/package*.json ./

COPY . .

WORKDIR /usr/src/app/frontend/src/main/web

RUN npm cache clean --force && npm install

RUN npm rebuild node-sass

RUN npm install -g @angular/cli

EXPOSE 4200

ENV PORT 4200

CMD ng serve --host 0.0.0.0


