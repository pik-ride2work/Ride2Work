FROM node:latest

EXPOSE 4200

WORKDIR /usr/src/app

COPY frontend/src/main/web/package*.json ./

COPY . .

WORKDIR /usr/src/app/frontend/src/main/web

RUN npm install

RUN npm rebuild node-sass

RUN npm run build

CMD npm start


