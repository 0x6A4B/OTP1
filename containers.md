# Containers

## Spring Boot API

### Running API container

```
docker run -p 80888:8088 -d
```

Set environment variables:
```
MARIADB_URI
MQTTBROKER_URI
MQTTBROKER_PORT
JWT_SECRET
DB_USER
DB_PASSWORD
```

Example as in our demo:
```
MARIADB_URI="192.168.1.103"
MQTTBROKER_URI="tcp://otp1.0x6a4b.dev"
MQTTBROKER_PORT="1883"
JWT_SECRET="4eef489576bc5307ae9cc9df1173a70a3bfa54fd77b8263cea61749d044ea9ba"
DB_USER="appuser"
DB_PASSWORD="appuser"
```


## Client

### Running VNC client container

Running VNC container, removing after run stopped
```
docker pull 0x6a4b/otp:client
docker run --rm --publish 5900:5900 0x6a4b/otp:client -d
```

Connecting to it
```
vncviewer localhost:5900 
```

Tunneling to server if run remotely
```
ssh -L 5900:localhost:5900 USER@REMOTESERVER -p PORT
```


## Faker

### Running the faker container

With the current dir being the volume dir for sensors.list

```
podman run -p 1883 -v ./:/app/data faker:latest -d
```

### Adding devices to the faker

UUID must be valid and in quotes

```
mosquitto_pub -h otp1.0x6a4b.dev -m "VALID_UUID" -t sensor/data/add_device -i client_name -u test_user -P test1234 -d
```