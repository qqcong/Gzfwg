package com.bwoil.pay.gateway.jedis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	
	@Bean("jedis.config")
    public JedisPoolConfig jedisPoolConfig() {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(5);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(10000);
        config.setMaxTotal(100);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(true);
        // 是否启用pool的jmx管理功能, 默认true
        config.setJmxEnabled(true);
        return config;
    }

    @Bean("jedis.pool")
    public JedisPool jedisPool(
            @Qualifier("jedis.config") JedisPoolConfig config, 
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") int port) {
        return new JedisPool(config, host, port);
    }
    
    @Bean
    public Jedis jedis(@Qualifier("jedis.pool") JedisPool jedisPool) {
    	return jedisPool.getResource();
    }

}
