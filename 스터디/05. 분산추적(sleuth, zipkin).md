# 분산추적

## Zipkin 분산추적
```
    https://zipkin.io/
    span: 64 bit unique id로 하나의 요청에 사용되는 작업단위    
    trace; 트리구조로 이루어진 span set

    Docker
        docker run -d -p 9411:9411 openzipkin/zipkin

    java
        curl -sSL https://zipkin.io/quickstart.sh | bash -s
        java -jar zipkin.jar        

```

## Sleuth => Zipkin
```
    스프링 애플리케이션과 집킨 연동시 켜줌
    Trace ID    AAA       AAA     AAA
    Span ID     AAA       A01     A02
```

### 애플리케이션 설정
```
    pom.xml 의존성 추가
    
```