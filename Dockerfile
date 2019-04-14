FROM tomcat:8.0.20-jre8

EXPOSE 8080

COPY backend/target/backend-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ride2work/ride2work.war