FROM node:8.11.2-alpine as node

WORKDIR /usr/src/app

COPY frontend/src/main/web/package*.json ./

RUN npm install

COPY . .

WORKDIR /usr/src/app/frontend/src/main/web

RUN npm run build


