FROM 0x6a4b/vnc-gui
ARG JAR_FILE=target/I-SPY-U*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]