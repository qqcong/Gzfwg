package com.bwoil.pay.gateway.timer;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.apachecommons.CommonsLog;
import redis.clients.jedis.Jedis;

@CommonsLog
@Component
public class RedisLock {
	
	@Autowired
    private Jedis jedis;
	
    /**
     * 获取锁
     * @return
     */
    public boolean getLock(String key, String val, long timeOut) {
    	log.info("getLock KEY: "+key);
        String result = jedis.set(key, val, "NX", "EX", timeOut);
        log.info("getLock result: "+result);
        if (StringUtils.equalsIgnoreCase(result, "OK")) {
            return true;
        }
        return false;
    }

    /**
     * 释放锁
     */
    public void releaseLock(String key, String val){
    	log.info("releaseLock KEY: "+key);
    	String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(val));
        log.info("releaseLock KEY result: "+result);
    }
}
