Suggested Answer for:
======================
http://stackoverflow.com/questions/38716703/disable-resolving-login-parameters-passed-as-url-parameters
Project based on: https://github.com/spring-guides/gs-securing-web

git clone

cd q38716703

mvn clean install

mvn spring-boot:run&

curl -X POST http://127.0.0.1:8080/login?username=user&password=password