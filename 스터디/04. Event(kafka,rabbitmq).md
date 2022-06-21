### 남아 있는 도커 이미지 전체 제거
    조...회: docker container ls -a
    제...거: docker system prune 
    확...인: docker container ls -a

### 네트워크 구성
```
    조회: docker network ls  ==> 기본네트워크 [ brige host none ]
    생성: docker network create --gateway 172.18.0.1 --subnet 172.18.0.9/16 ecommerce-network
    내용확인: docker network inspect '네트워크 ID'
```

### RabbitMQ
```
    docker run -d \
        --name rabbitmq rabbitmq:management \
        --network ecommerce-network \
        -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 \
        -e RABBITMQ_DEFAULT_USER=guest \
        -e RABBITMQ_DEFAULT_PASS=guest 

    docker run -d --name rabbitmq --network ecommerce-network -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 rabbitmq:3-management
```



