FROM 0x6a4b/x-gui

ARG JAR_FILE=target/I-SPY-U.jar

COPY ${JAR_FILE} app.jar

#COPY docker_xinitrc /root/.xinitrc
#ENTRYPOINT ["x11vnc", "-create"]
ENTRYPOINT ["java", "-jar", "app.jar"]