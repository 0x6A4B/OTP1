# I-SPY-U API backend - spring boot


### Deployement


Firewall
```
firewall-cmd --zone=public --add-port=8088/tcp
```

Maven packaging => JAR
```
./mvnw package
```

Dockerizing
```
docker build -t otp1 .
```

Docker run
```
docker run otp1 -p 8088:8088
```


