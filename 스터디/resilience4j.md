# Resilience4j

## install
```
    문서: https://resilience4j.readme.io/docs/getting-started
    설치: https://github.com/resilience4j/resilience4j
```

## spring 설정
```
    * pom.xml에 추가
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
    </dependency>

    * 커스텀 설정
    @Configuration
    public class Resilience4JConfiguration {
        @Bean
        public Customizer< resilience4jCircuitBreakerFactory >
            CircuitBreakerConfig circuitBreakerConfig = cirguitBreakerConfig.custom();
                .failureRateThreshold(4)
                .waitDurationInOpenState(Duration.ofMillis(1000) )
                .slidngWindowType( circuiBreakerConifg.SlidingWindowType.COUNT_BASED)
                .slidingWindowsSize(2)
                .build()
        }
    }

    * implement
    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

    /* Circuitbreaker */
    CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
    List<ResponseOrder> orderList = circuitBreaker.run( 
        () -> orderServiceClient.getOrders(userId),
        throwable -> new ArrayList<>()
    );
    
    userDto.setOrders(orderList);
```