# Sonarqube


## Install

### Prerequisites

- PostgreSQL

### Docker

Limits

```
sysctl -w vm.max_map_count=524288
sysctl -w fs.file-max=131072
ulimit -n 131072
ulimit -u 8192
```

Volumes

```
docker volume create --name sonarqube_data
docker volume create --name sonarqube_logs
docker volume create --name sonarqube_extensions
```

/opt/sonarqube/data: data files, such as the embedded H2 database and Elasticsearch indexes
/opt/sonarqube/logs: contains SonarQube logs about access, web process, CE process, Elasticsearch logs
/opt/sonarqube/extensions: for 3rd party plugins

Run Docker

```
docker run --name sonarqube \
-v sonarqube_data:/opt/sonarqube/data \
-v sonarqube_logs:/opt/sonarqube/logs \
-v sonarqube_extensions:/opt/sonarqube/extensions \
-p 9000:9000 sonarqube:community
```
