FROM 0x6a4b/x-gui

ARG JAR_FILE=../target/I-SPY-U.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
