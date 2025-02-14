# Install and setup

# Jenkins


## Jenkins docker container and versions

Jenkins latest official docker image is unfortunately, and surprisingly, using JDK17 but we need JDK21 so we need to update JDK.

It also uses Debian Bookworm as the Linux OS in the container so the Maven offered in the repository is version 3.8.7 instead of the current latest 3.9.9 and there are incompatibilities which there is no reason in addressing. So instead we will also update Maven to the latest.


### JDK 21 and Maven

We have two main options, either we remove the current configured Jenkins container and pull the latest with JDK21

```
docker run jenkins/jenkins:latest-jdk21 -p 8080:8080 -p 50000:50000
```

Or we download the binaries/package from Oracle. As Oracle offers the .deb package ready to be added with packagemanager and we already have configured Jenkins partly we'll rather go this way

```
docker exec -it -u root [CONTAINER_NAME] bash

cd
mkdir temp && cd $_

apt update && apt install wget

wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.deb

dpkg -i jdk-21_linux-x64_bin.deb

/usr/lib/jvm/jdk-21.0.6-oracle-x64/bin/java --version

wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
wget https://downloads.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz.sha512

echo -e "$(cat apache-maven-3.9.9-bin.tar.gz.sha512)  apache-maven-3.9.9-bin.tar.gz" | shasum --check

tar xvf apache-maven-3.9.9-bin.tar.gz -C /opt

ln -s /opt/apache-maven-3.9.9 /opt/maven
ln -s /opt/maven/bin/mvn /bin/mvn

mvn --version
```

1. On server enter the docker container in interactive terminal mode executing bash (/bin/bash) as root user
2. Go to home directory if not already there
3. Create temp directory and move into it
4. Update repository package index and install wget
5. Use wget to download new JDK
6. Let's check that it works and the version is correct
7. Use wget to download newest maven
8. And now the checksum file, which we should have done with the JDK too...
9. Let's check the checksum with shasum. Do note the 2 spaces between checksum and filename in the echo command
10. Extract the gzipped tarball to /opt
11. Create a symbolic link of /opt/apache-maven-3.9.9 as /opt/maven so it's easy to update Maven if need to
12. Create a symbolic link of current Maven (/opt/maven not /opt/apache-maven...) to /bin so mvn command is in the path OR add MAVEN_HOME and .M2_HOME and /opt/maven/bin to PATH, which would be the more correct way. But this is quick and dirty alternative
13. And now we have mvn in path and can check the version


## Jenkins configuration

### JDK and Maven

**JDK**

We are setting up the specific JDK21

Name: jdk21
JAVA_HOME: /usr/lib/jvm/jdk-21.0.6-oracle-x64/


**Maven**

We are setting the global Maven

Name: current
MAVEN_HOME: /opt/maven


### Plugins

- For JaCoCo we use plugin called Coverage
- Blue Ocean
- Docker
- Maven Integration


## Jenkins job

### Backend API



### Client


## Docker hub

https://hub.docker.com/repository/docker/0x6a4b/otp

docker push 0x6a4b/otp:tagname
docker login -u 0x6a4b
ACCESS TOKEN: [CENSORED]


### Webhook for kubernetes

For Kubernetes to pull new image and deploy it


## Reverse proxy

### NGINX

## Docker remote access for jenkins

docker.service
/usr/lib/systemd/system/docker.service

ExecStart=/usr/bin/dockerd --add-runtime oci=/usr/sbin/runc -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375 $DOCKER_OPTS


sudo systemctl daemon-reload
sudo systemctl restart docker.service

start containers not set to autostart
- Jenkins: docker start condescending_booth
- API: docker start adoring_saha

docker ps

### In Jenkins

- Create new cloud
    - tcp://192.168.1.103:2375 - our server hosting the docker

- Docker agent template
    - java-build-slave


### Docker slave image

Dockerfile 

```Dockerfile
FROM debian:bookworm

LABEL maintainer="0x6A4B <0x6A4B@proton.me>"

# Updating package sources
RUN apt update && \
    apt -qy full-upgrade && \
    apt install -qy git && \
    apt install -qy wget && \
# Install a basic SSH server
    apt install -qy openssh-server && \
    sed -i 's|session    required     pam_loginuid.so|session    optional     pam_loginuid.so|g' /etc/pam.d/sshd && \
    mkdir -p /var/run/sshd && \
# Installing JDK 21
   # apt-get install -qy openjdk-21-jdk && \
    mkdir temp && cd $_ && \
    wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.deb && \
    dpkg -i jdk-21_linux-x64_bin.deb && \
# Install Maven 3.9.9
    wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz && \
    tar xvf apache-maven-3.9.9-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.9.9 /opt/maven && \
    ln -s /opt/maven/bin/mvn /bin/mvn && \
    cd .. && rm -r temp && \
# Package cleanup
    apt-get -qy autoremove

# Add jenkins user
RUN useradd -m -d /home/jenkins -s /bin/bash jenkins && \
# Set password for the jenkins user (you may want to alter this).
    echo "jenkins:jenkins" | chpasswd && \
    mkdir /home/jenkins/.m2

# Copy Jenkins settings file
#COPY settings.xml /home/jenkins/.m2/

# Copy authorized keys
COPY authorized_keys /home/jenkins/.ssh/authorized_keys

RUN chown -R jenkins:jenkins /home/jenkins/.m2/ && \
    chown -R jenkins:jenkins /home/jenkins/.ssh/

# Standard SSH port
EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]
```