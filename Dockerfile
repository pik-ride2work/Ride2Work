FROM node:latest

WORKDIR /usr/src/app

COPY frontend/src/main/web/package*.json ./

COPY . .

WORKDIR /usr/src/app/frontend/src/main/web

RUN npm install

RUN npm run build


