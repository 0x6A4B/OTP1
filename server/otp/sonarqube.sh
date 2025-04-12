#!/bin/bash
echo "Starting sonarqube"

SONAR_TOKEN=sqp_6d3f9a7b89cb51cd8dca235f8dc3667e9f58326c

mvn clean verify sonar:sonar \
  -f server/otp/pom.xml \
  -Dsonar.projectKey=OTP-Server \
  -Dsonar.projectName='OTP-Server' \
  -Dsonar.host.url=http://192.168.1.103:9000 \
  -Dsonar.token=$SONAR_TOKEN