package com.autumnia.aaservice;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

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
		tomcat.addAdditionalTomcatConnectors(createStandardHttpConnector()); // 톰캣에 Connector 추가
		return tomcat;
	}

	// 톰켓의 경우 기본적으로 포트가 8080 으로 생성 된다. 
	private Connector createStandardHttpConnector() {
	    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	    connector.setPort(80); 
	    return connector;
	}	


}
