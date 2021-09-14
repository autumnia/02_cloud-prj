# 네트워크 확인
# $ sudo docker network ls 
# 아래 포트는 방화벽에서 열려 있어야 한다.
# 기본계정은 admin 

docker run -it --name rabbitmq -h rabbitmq \
-p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 \ 
-e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest \
--network ecommerce-network \
//--net ecommerce-network --net-alias=rabbitmq
-d rabbitmq:management