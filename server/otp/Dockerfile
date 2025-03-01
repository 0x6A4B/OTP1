FROM openjdk:21

# Setting environment variables, a bad way to handle secrets
# DO NOT USE in real production, especially if public

ENV MARIADB_URI="172.18.0.3"
ENV MQTTBROKER_PORT="1883"
ENV MQTTBROKER_URI="tcp://otp1.0x6a4b.dev"
ENV JWT_SECRET="4eef489576bc5307ae9cc9df1173a70a3bfa54fd77b8263cea61749d044ea9ba"
ENV DB_USER="appuser"
ENV DB_PASSWORD="appuser"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
