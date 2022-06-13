 eclipse run configurations
	vm arguments 항목 => -Dserver.port=9001~900N

# maven 기동 방법

```
	mvnw spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=9001~900N'
```

# maven 컴파일 그리고 jar 실행
```
	mvnw clean compile package
	java -jar -Dserver.port=9004 ./target/userservice-0.0.1-SNAPSHOT.jar
```

# application.yml 수정 후
	${spring.cloud.client.hostname}:${spring.application.instance_id}:${random.value}
	mvnw spring-boot:run


API gateway
	
	MSA간 통신 방법
		RestTemplate
		Feign Client

	spring boot 2.4에서 미사용 됨
		Ribbon  <= client side load balancer ( 비동기 방식 )
		Zull	<= server side load balancer 
		gateway (비동기 방식 )
		
cmd 추가
	.\mvnw clean compile package		
	.\mvnw spring-boot:run


git 
	git remote -v
	git remote add origin https://github.com/autumnia/mycloud.git
	git push  --set-upstream origin master  <== 맨 처음 한번만
	git push 

# docker
	설치 주소
	https://www.docker.com/products/docker-desktop
	http://localhost/tutorial/ <= 설치 후 튜토리얼 

	이미지 주소
	https://hub.docker.com

    도커 명령어 
	docker images | grep 16.04   <= 이미지가 많을 경우 linux 명령어로 찾는다. 
	docker images == docker image ls
	docker container ls 
	docker container ls -a == docker ps -a   <= 상세 조회

	docker pull ubuntu:xenial-20210611
	docker create ubuntu:xenial-20210611
	run = pull + create

	docker run ubuntu  <== 태그가 없는 경우 최신 버전 

	-d 		: detached mode
	-p 		: port 포워딩
	-v 		: 호스트 컨테이너의 폴더 공유
	-e 		: 컨테이너 내에서 사용할 환경변수 설정
	-name 	: 컨테이너 이름 정의
	-rm 	: 프로세스 종료시 컨테이너 자동 제거
	-it 	: input, tty <= 터미널
	-link 	: 컨테이너 연결 ?????

	예) docker run -d -p 80:80 ubuntu:16.04

	mysql 
		docker run -d -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mysql mysql:5.7
		docker exec -it mysql /bin/bash

	mariadb
		// 저장소 생성
		docker volume create  mariadb_data
		docker volume ls <= 생성 확인
		
		// 컨테이너 생성시 저장소 연결
		docker run -d -p 3306:3306 -v mariadb_data:/var/lib/mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mariadb mariadb:latest
		docker run -d -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mariadb mariadb:latest

		
		docker logs containerID  <- 로그 확인 
		docker exec -it mariadb /bin/bash
		
		docker stop mariadb
		docker start mariadb

		docker rm containerID <= 중지가 아니라 제거 

	DockerFile
		docker build  -t  autumnia/user-service:1.0  .   <== 도커 파일 빌드
		docker push autumnia/user-service:1.0
		docker rmi imageID <= 입력
		docker pull autumnia/user-service:1.0

	Docker network 생성
		docker network ls
		docker network create --gateway 172.18.0.1 --subnet 172.18.0.0/16 ecommerce-network
		docker network create ecommerce-network
		docker network inspect ecommerce-network  <== 상세정보

	미사용 중인 리소스 정리
		docker system prune  
