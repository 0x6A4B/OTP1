FROM openjdk:21
ARG JAR_FILE=target/Faker.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]