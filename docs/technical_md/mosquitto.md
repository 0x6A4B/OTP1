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
