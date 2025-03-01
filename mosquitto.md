# Mosquitto


## Docker

```
docker run -d --name mosquitto -p 1883:1883 \
-v /home/[USER]/mosquitto/config:/mosquitto/config \
-v /home/[USER]/mosquitto/logs:/mosquitto/logs \
-v /home/[USER]/mosquitto/data:/mosquitto/data \
#-v /home/[USER]/mosquitto/passwd:/mosquitto/passwd \
eclipse-mosquitto
```

Or using docker compose
```
docker compose up -d
```

```
docker exec -it -u 1883 mosquitto bash
```

### Password authentication

In docker container as user mosquitto

```
mosquitto_passwd -c /mosquitto/passwd_file [USER]
```
E.g. as user IOT with password IOT for sensor devices

Remove user
```
mosquitto_passwd -D /mosquitto/passwd_file [USER]
```

Add users
```
mosquitto_passwd -b /mosquitto/passwd_file [USER] [PASSWORD]
```


### Testing mosquitto

Publish (-r for retain)
```
mosquitto_pub -h [HOSTNAME] -m "test" -t test/test -i testclient -d
```

Clear retain
```
mosquitto_pub -h [HOSTNAME] -n -r -t test/test -i testclient -d
```

With user and password auth
```
mosquitto_pub -h [HOSTNAME] -m "test" -t test/test -u [USER] -P [PASSWORD] -d
```

Subscribe
```
mosquitto_sub -h [HOSTNAME] -t test/#
```


## Running the faker container

With the current dir being the volume dir for sensors.list

```
podman run -p 1883 -v ./:/app/data faker:latest -d
```

## Adding devices to the faker

UUID must be valid and in quotes

```
mosquitto_pub -h otp1.0x6a4b.dev -m "VALID_UUID" -t sensor/data/add_device -i client_name -u test_user -P test1234 -d
```