package com.bwoil.pay.gateway;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.bwoil.pay.gateway.util.SignUtils;


@ComponentScan("com.bwoil")
@EntityScan("com.bwoil")
@SpringBootApplication
public class Startup {

    public static void main(String[] args) {
    	ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Startup.class).web(true).run(args);
    	String privateKey = ctx.getEnvironment().getProperty("pay.sign.privateKey");
    	SignUtils.privateKey = privateKey;
    	System.out.println(">>>>>>>>>>>>privateKey: "+(StringUtils.isBlank(privateKey) ? "": privateKey.substring(0,4)));
    }
}
