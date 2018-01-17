package com.bwoil.pay.gateway.timer;

import com.bwoil.common.framework.data.annotation.Condition;
import com.bwoil.common.framework.data.annotation.MatchMode;
import com.bwoil.common.framework.form.BaseQueryForm;
import com.bwoil.common.framework.result.QueryResult;
import com.bwoil.pay.common.constants.TradeStatus;
import com.bwoil.pay.common.dao.TransactionDao;
import com.bwoil.pay.common.entity.Transaction;
import com.bwoil.pay.common.pay.result.PayResult;
import com.bwoil.pay.gateway.form.pay.QueryPayForm;
import com.bwoil.pay.gateway.result.pay.PayOrderInfo;
import com.bwoil.pay.gateway.service.PayInfoNotify;
import com.bwoil.pay.gateway.service.PayService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 查找正在处理中或未完成的支付订单
 */
@Component
@EnableScheduling
@CommonsLog
public class QueryPayTask {

    @Autowired
    private PayService payService;


    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private RedisLock lock;
    @Autowired
    private PayInfoNotify notify;
    private final static String LOCK_KEY="PAY:QUERY:TASK:LOCK";
    private static final long DEFAULT_EXPIRE = 360;//80s 有慢sql，超时时间设置长一点
    /**
     * 一分钟执行一次任务
     */
//    @Scheduled(fixedDelay = 60*1000)
    @Transactional
    public void run(){
        log.info("执行 QueryPayTask");
        String uuid = UUID.randomUUID().toString();
        boolean locked=lock.getLock(LOCK_KEY, uuid, DEFAULT_EXPIRE);
        if(!locked){
            log.error("QueryPayTask get lock FAIL");
            return;
        }
        log.info("QueryPayTask get lock SUCC");
        try{
            excute();
        }catch (Exception e){
            log.error("执行 QueryPayTask 异常",e);
        }finally {
            lock.releaseLock(LOCK_KEY, uuid);
            log.info("QueryPayTask release lock SUCC");
        }
    }

    private  void  excute(){
        Map<Object,Object> params=new HashMap<>();
        params.put("notified",false);

        QueryResult<Transaction> qr = transactionDao.query(new BaseQueryForm() {
            @Condition(match = MatchMode.EQ)
            private Boolean notified=false;

            @Condition(match = MatchMode.GE)
            private Date created=new Date(System.currentTimeMillis()-48*60*60*1000);
            @Condition(match = MatchMode.IN)
            private TradeStatus[] tradeStatus={TradeStatus.FAIL,TradeStatus.ERROR,TradeStatus.SUCCESS};
            public Boolean getNotified() {
                return notified;
            }

            public Date getCreated() {
                return created;
            }
            public TradeStatus[] getTradeStatus() {
                return tradeStatus;
            }


        });
       List<Transaction> ts=qr.getData();

        if(ts!=null){
            for(Transaction t:ts){
                if(t.getTradeStatus()==TradeStatus.FAIL) {
                    QueryPayForm form = new QueryPayForm();
                    form.setTransId(t.getTransId());
                    form.setAppid(t.getAppid());
                    PayOrderInfo orderInfo = payService.query(form);
                    if (orderInfo != null) {
                        if ("SUCCESS".equals(orderInfo.getStatus()) || "ERROR".equals(orderInfo.getStatus())) {
                            PayResult result = new PayResult();
                            result.setTradeStatus(TradeStatus.valueOf(orderInfo.getStatus()));
                            result.setPayAmount(Integer.valueOf(orderInfo.getAmount()));
                            result.setTransactionNo(t.getTransactionNo());
                            notify.notify(result);
                        }
                    }
                }else{
                        notify.notify(t);
                }
            }
        }

    }
}
