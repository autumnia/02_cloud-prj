package com.autumnia.aaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArchitectureServiceApplication {


	public static void main(String[] args) {
//		SpringApplication.run(ArchitectureServiceApplication.class, args);
		// https 용 포트를 지정해 준다. 
		SpringApplication app = new SpringApplication(ArchitectureServiceApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "443"));
        app.run(args);
	}

	@Bean
	public ServletWebServerFactory serverFactory() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createStandardHttp2Connector()); // 톰캣에 Connector 추가
		return tomcat;
	}

	// 톰켓의 경우 기본적으로 포트가 8080 으로 생성 된다. 
	private Connector createStandardHttp2Connector() {
	    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	    connector.setPort(80); 
	    return connector;
	}	


}
