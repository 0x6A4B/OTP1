mvn clean verify sonar:sonar \
  -f client/I-SPY-U/pom.xml \
  -Dsonar.projectKey=OTP \
  -Dsonar.projectName='OTP' \
  -Dsonar.host.url=http://192.168.1.103:9000 \
  -Dsonar.token=$SONAR_TOKEN






  sqp_5e0a46dc20893b9b8fe5a2006b947d1c2a043bbd

  mvn clean verify sonar:sonar \
  -Dsonar.projectKey=OTP-Dev \
  -Dsonar.projectName='OTP-Dev' \
  -Dsonar.host.url=http://192.168.1.103:9000 \
  -Dsonar.token=sqp_5e0a46dc20893b9b8fe5a2006b947d1c2a043bbd