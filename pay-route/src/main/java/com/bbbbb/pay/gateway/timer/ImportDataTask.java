package com.bbbbb.pay.gateway.timer;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.gateway.form.bind.BindSubmitForm;
import com.bbbbb.pay.gateway.result.bind.BindComfirmResult;
import com.bbbbb.pay.gateway.service.BindService;
import com.bbbbb.userJdbc.CardBean;
import com.bbbbb.userJdbc.DataImport;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.transaction.annotation.Propagation;
import redis.clients.jedis.Jedis;

@Component
@EnableScheduling
@CommonsLog
public class ImportDataTask {
    @Autowired
    private BindService bindService;
    @Autowired
    private RedisLock lock;
    
    @Autowired
    private Jedis jedis;
    
    private static final long DEFAULT_EXPIRE = 360;//80s 有慢sql，超时时间设置长一点
    private final static String LOCK_KEY="PAY:INIT:TASK:LOCK";

    private final static String LAST_UPDATE_KEY="PAY:UPDATE:TASK:KEY";


    @Value("${pay.import.appid}")
    private String appid="test";
    @Value("${pay.import.url}")
    private String url = "jdbc:mysql://192.168.101.61:3306/b2c_v3.4?characterEncoding=utf8";
    @Value("${pay.import.user}")
    private String user = "root";
    @Value("${pay.import.password}")
    private String password = "bbbbb@20141030";

    public  void reImport(){
    	jedis.del(LAST_UPDATE_KEY);
    }
//    @Scheduled(fixedDelay = 5*1000)
   // @Transactional
    public void run() {
    	String uuid = UUID.randomUUID().toString();
        boolean locked=lock.getLock(LOCK_KEY,uuid, DEFAULT_EXPIRE);
        if(!locked){
            log.error("ImportDataTask get lock FAIL");
            return;
        }
        log.info("ImportDataTask get lock SUCC");
        String l=jedis.get(LAST_UPDATE_KEY);
        log.info("LAST_UPDATE_KEY: "+ l);
        long last=0L;
        if(l!=null){
            last=Long.parseLong(l);
        }
        try {
            DataImport di=     new DataImport() {
                @Override
                public void post(List<CardBean> list) {
                    for (CardBean card : list) {
                        BindSubmitForm form = new BindSubmitForm();
                        form.setBankCode(card.getBankCode());
                        form.setCardNo(card.getCardNum());
                        form.setIdCardNo(card.getIdNumber());
                        form.setMobile(card.getMobile());
                        form.setTimestamp(System.currentTimeMillis()+"");
                        form.setUserId(card.getUserId());
                        form.setUserName(card.getName());
                        form.setChannel("BAOFU");
                        form.setAppid(appid);
                        form.setRequestNo(UUID.randomUUID().toString());

                        jedis.set(LAST_UPDATE_KEY,card.getLastUpdate()+"");
                        try {
                            BindComfirmResult directBind = bindService.directBind(form);
                            System.out.println(JsonUtils.toString(directBind));
                        } catch (Exception e) {
                            log.debug("导入新生数据绑定错误");
                        }
                    }
                }
            };
            di.setPassword(password);
            di.setUrl(url);
            di.setUser(user);
            di.execute(last);
        }catch (Exception e){
            log.error("导入数据错误",e);
        }finally {
            lock.releaseLock(LOCK_KEY, uuid);
            log.info("ImportDataTask release lock SUCC");
        }
    }
}
