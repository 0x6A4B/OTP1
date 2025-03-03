# JavaFX and other GUI applications in Docker container

## Dockerfile for building an image with VNC server


[https://github.com/0x6A4B/vnc-gui-docker](https://github.com/0x6A4B/vnc-gui-docker)


Simple Dockerfile to have somewhat minimal Debian install with X Window System.  
Uses xfvb virtual framebuffer for virtual graphics card and x11vnc for a simple VNC server.

Includes OpenBox available as window manager as it takes only few megabytes.  
Also includes JDK21 as the Debian version was 17 at the time of creating this. 



```
FROM debian:bookworm

LABEL maintainer="0x6A4B <0x6A4B@proton.me>"

RUN apt update && \
    apt -qy full-upgrade && \
    apt install -qy git && \
    apt install -qy wget && \
# Minimal X environment
    apt install -qy xorg && \
    apt install -qy xinit && \
    apt install -qy openbox && \
#
    apt install -qy x11vnc && \
    apt install -qy xvfb && \
# Installing JDK 21
   wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.deb && \
   dpkg -i jdk-21_linux-x64_bin.deb && \
   rm jdk-21_linux-x64_bin.deb && \
#
    apt autoclean -qy && \
    apt autoremove -qy



CMD ["x11vnc", "-create", "-forever"]
```


### Docker image

[https://hub.docker.com/repository/docker/0x6a4b/vnc-gui](https://hub.docker.com/repository/docker/0x6a4b/vnc-gui)



Docker pull and run

```
docker run --rm --publish 5900:5900 0x6a4b/vnc-gui
```
Pull the image with Debian and X11 VNC server running it and exposing VNC port 5900 for VNC connection

--rm removes the container after running it


Connect to the X11 server in the container with a VNC client, for example TigerVNC
```
vncviewer localhost:5900
```

You have now access to the X window server inside the container running xvfb virtual framebuffer



You can tunnel into the server running the container to access it remotely without exposing the 5900 port on the server

```
ssh -L 5900:localhost:5900 USER@HOST.SERVER -p SSH_PORT
```
Allows connecting to the VNC remotely from another computer without exposing the 5900 port to outside. Requires an SSH server running on the computer running the Docker.  
Now you can just connect the VNC client to localhost:5900 as it is forwarded to the remote server running the SSH server and Docker container via secure shell tunnel.


### Running JavaFX applications on the VNC server

You can test your .JAR file by copying it into this docker container, logging in with VNC and running it there

After running the container to get the name of the container
```
docker ps
```

Output:
```
CONTAINER ID  IMAGE                            COMMAND               CREATED       STATUS        PORTS                   NAMES
71c59f9cf6b7  docker.io/0x6a4b/vnc-gui:latest  x11vnc -create -f...  1 second ago  Up 2 seconds  0.0.0.0:5900->5900/tcp  distracted_banach
```

We can recognize the correct container from the port 5900 the VNC server is sharing.

Now copy our app.jar file to the container:
```
docker cp app.jar distracted_banach:/
```

Connect to it with VNC client and run in the xterm:
```
java -jar app.jar
```
If the app.jar isn't found we can diagnose the situation a bit with
```
pwd
ls ./*.jar
```
First command prints current working directory and the second one lists all .jar files in the current directory.
If the ls command doesn't list our .jar then we are either in the wrong directory or it wasn't copied in here for some reason.


Did you copy it to the right container and in to the root, and are we in the root now?


### Building our JavaFX container

Dockerfile
```
FROM 0x6a4b/vnc-gui

ARG JAR_FILE=target/JAVA_APP.jar

COPY ${JAR_FILE} app.jar

COPY docker_xinitrc /root/.xinitrc
ENTRYPOINT ["x11vnc", "-create"]
```

We pull the aforementioned container with the VNC server and set it start the vnc server when container is run. Copy the JAVA_APP.jar we have built and also copy the .xinitrc to the container which will be run when a user logs in.

It can be as simple as: 

.xinitrc
```
exec java -jar app.jar
```

This runs the .JAR packaged java application on user connect.  
Do note that JavaFX application can not start without a display to connect to. This is why we start the java application on user connect and not on docker start.  
Not the only way to handle this but a simple and pragmatic way.



### Dependencies for the JAR package

The JavaFX is not a standard Java library anymore so it won't be included on default installation. It must be either installed on the container or packaged within the JAR file.

Here is an example of pom.xml plugin tags for including JavaFX and other dependencies within the JAR file so it can be run on any system with Java runtimes. We are also defining the main class for the manifesto.


pom.xml
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.6.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <finalName>I-SPY-U</finalName>
                <transformers>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>Main</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```