#!/bin/bash
echo "Starting sonarqube"

SONAR_TOKEN=sqp_e4e859d9cac79c1d19a2832690208fb3a2e3df64

mvn clean verify sonar:sonar \
  -f client/I-SPY-U/pom.xml \
  -Dsonar.projectKey=OTP \
  -Dsonar.projectName='OTP' \
  -Dsonar.host.url=http://192.168.1.103:9000 \
  -Dsonar.token=$SONAR_TOKEN



echo "Starting publishing of JAR to GitHub releases..." &&

RELEASE_BODY=$(git log -1 | tail -n 1 | awk '{gsub(/  /, "", $0); print}')

RELEASE_ID=$(curl -i -L \
    -X POST \
    -H "Accept: application/vnd.github+json" \
    -H "Authorization: Bearer $GITHUB_TOKEN" \
    -H "X-GitHub-Api-Version: 2022-11-28" \
    https://api.github.com/repos/0x6A4B/OTP1/releases \
    -d '{"tag_name":"'"$(date +%Y%m%d-%H%M)"'","target_commitish":"main","name":"I-SPY-U Client","body":"'"$RELEASE_BODY"'","draft":false,"prerelease":false,"generate_release_notes":false}' \
    | grep id | head -n2 | tail -n1 | awk '{print substr($2, 1, length($2-1))}') &&


curl -i -L \
    -X POST \
    -H "Accept: application/vnd.github+json" \
    -H "Authorization: Bearer $GITHUB_TOKEN" \
    -H "X-GitHub-Api-Version: 2022-11-28" \
    -H "Content-Type: application/octet-stream" \
    "https://uploads.github.com/repos/0x6A4B/OTP1/releases/$RELEASE_ID/assets?name=I-SPY-U.jar" \
    --data-binary "@target/I-SPY-U.jar"
