FROM 0x6a4b/vnc-gui

ARG JAR_FILE=target/I-SPY-U.jar

COPY ${JAR_FILE} app.jar

COPY docker_xinitrc /root/.xinitrc
ENTRYPOINT ["x11vnc", "-create", "-forever"]
