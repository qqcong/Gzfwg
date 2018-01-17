package com.bwoil.pay.bss;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@ComponentScan("com.bwoil")
@EntityScan("com.bwoil")
@SpringBootApplication
public class Startup {
	
    public static void main(String[] args) {
    	ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Startup.class).web(true).run(args);
    	String port = ctx.getEnvironment().getProperty("server.port");
    	log.info("tomcat port: "+port);
    }
}
