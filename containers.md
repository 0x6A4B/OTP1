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

### Running X Display client container

This seems to differ quite a lot on different distros  
And with Windows you need to install vcxsrv program to have X Server in windows

X Server is configured to not allow connections from outside and doesn't listen to TCP at all


Run container:
```
podman run --rm -it --net=host --env DISPLAY=$DISPLAY 0x6a4b/otp:clientX
```

If container can't find the host's xserver, maybe you need to supply IP?
```
podman run --rm -it --net=host --env DISPLAY=127.0.0.1$DISPLAY 0x6a4b/otp:clientX
```

There might be need to allow x connections:
```
xhost +local:docker
# if not working oyu can try allowing all, not a safe option but helps diagnosing
xhost +
# you can reverse it with xhost -

# you might want to check if host is listening to 6000 for display 0, 6001 for diplay 1
# you can see your display with echo $DISPLAY
nmap localhost
# check access rights for .Xauthority file
ls -l $XAUTHORITY
# add read rights to all
chmod 644 $XAUTHORITY
```

some linux distros like Fedora ,or perhaps it's related to Gnome, need to be told to listen to X TCP
if nmap shows none
add to /etc/gdm/custom.conf and restart gdm with sudo service gdm restart


```
[security]
DisallowTCP=false

[xdmcp]
ServerArguments=-listen TCP
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
