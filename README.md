# marathon-client-api-practice

https://github.com/mohitsoni/marathon-client

https://docs.mesosphere.com/1.8/usage/marathon/rest-api/#/

https://docs.mesosphere.com/1.10/deploying-services/marathon-api/

http://mesosphere.github.io/marathon/api-console/index.html

https://github.com/OpenFeign/feign

http://devflow.github.io/retrofit-kr/

###################################################################


spring boot docker 참조 : https://github.com/spring-guides/gs-spring-boot-docker

docker image 생성 ./gradlew build buildDocker

docker 실행 docker run -p 8899:8899 -t com.example/rest-api-service:0.0.1-SNAPSHOT


######################################################################

marathon 설치

https://github.com/uzyexe/mesos-marathon-demo

marathon 배포

curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" localhost:8080/v2/apps -d @sample/hello-world.json

######################################################################


rest-api-service 마라톤 배포

로컬에 rest-api-service docker image를 만들어 놔야함

cd /Users/ksb/Documents/git/marathon-client-api-practice/mesos-marathon-demo

curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" localhost:8080/v2/apps -d @spring/rest-api-service.json


######################################################################

curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" localhost:8080/v2/apps -d @nginx/nginx.json


######################################################################

http://blog.napagoda.com/2017/10/secure-spring-boot-rest-api-using-basic.html

curl -X GET -u test:secret  http://localhost:8899/get?param=test

http://test:secret@localhost:8899/get?param=test
