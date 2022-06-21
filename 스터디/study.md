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

