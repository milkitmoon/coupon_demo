package com.milkit.app;

import java.sql.SQLException;

//import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder biulder) {
		return biulder.sources(DemoApplication.class);
	}
	
/*		외부 DB툴에서 메모리 DB접근을 원할 경우 주석해제 (활성화 시 일부 Junit 테스트 실패할 수 있음)
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
	    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9099");
	}
*/

}