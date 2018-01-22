package com.bbbbb.pay.gateway.service.impl;

import com.bbbbb.common.framework.util.HttpUtils;
import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.common.constants.TradeStatus;
import com.bbbbb.pay.common.dao.BankCardDao;
import com.bbbbb.pay.common.dao.TransactionDao;
import com.bbbbb.pay.common.entity.BankCard;
import com.bbbbb.pay.common.entity.Transaction;
import com.bbbbb.pay.common.pay.result.PayResult;
import com.bbbbb.pay.gateway.result.pay.PayOrderInfo;
import com.bbbbb.pay.gateway.service.PayInfoNotify;
import com.bbbbb.pay.gateway.util.SignUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Service
@CommonsLog
@EnableAsync
public class PayInfoNotifyImpl implements PayInfoNotify {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private BankCardDao bankCardDao;

    @Async
    @Override
    @Transactional
    public void notify(Transaction transaction) {


        if(transaction.getTradeStatus()==TradeStatus.FAIL||transaction.getTradeStatus()==TradeStatus.WAIT||transaction.getTradeStatus()==TradeStatus.PROCESSING){
            return;
        }

        String url=transaction.getCallbackUrl();
        PayOrderInfo payResult=new PayOrderInfo();

        payResult.setTransId(transaction.getTransId());
        payResult.setStatus(transaction.getTradeStatus().name());
        payResult.setOrderNo(transaction.getOrderNo());
        payResult.setAppid(transaction.getAppid());
        if(transaction.isPaid()){
            payResult.setCode("0");
            payResult.setChannel(transaction.getChannel().getPayChannel().getCode());
            BankCard bankCard=bankCardDao.findById(transaction.getCardId());
            payResult.setBindId(bankCard.getBindId());
            payResult.setAmount(transaction.getPayAmount()==null?"0":transaction.getPayAmount().toString());
            payResult.setBankCode(unBankCode(bankCard.getBank().getBankCode()));
        }else{
            payResult.setCode(transaction.getFailureCode());
            payResult.setMsg(transaction.getFailureMsg());
        }

        SignUtils.sign(payResult);

        String sign=payResult.getSign();
        Map<String, String> map = SignUtils. transfer(payResult);
        map.put("sign",sign);
        /*if(map.containsKey("msg")) {
            try {
                map.put("msg", URLEncoder.encode(map.get("msg"),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }*/
        log.info("回调信息:"+ JsonUtils.toString(map));
        boolean success=false;
        try {
            String response = HttpUtils.httpPost(url, map, "UTF-8", 1000 * 5);
            success = "SUCCESS".equals(response);
            if (success) {
                transaction.setNotified(true);
                transactionDao.save(transaction);
            }
            log.info("回调返回数据"+response);
        }catch (Exception e){
            log.error("通知业务系统错误",e);
        }
      //  return success;
    }

    @Transactional
    @Override
    public void notify(PayResult result) {
        String transactionNo= result.getTransactionNo();
        Transaction transaction=  transactionDao.findOneByProperty("transactionNo",transactionNo);

        transaction.setFailureCode(result.getErrCode());
        transaction.setFailureMsg(result.getErrMsg());
        transaction.setTradeStatus(result.getTradeStatus());
        transaction.setPaid(result.getTradeStatus()== TradeStatus.SUCCESS);
        transaction.setPayAmount(result.getPayAmount());
        transactionDao.save(transaction);
        notify(transaction);
    }

    private Map<String,String> bankCodeMap=new HashMap<>();

    @PostConstruct
    public void init(){
        bankCodeMap.put("CITIC","CNCB");
        bankCodeMap.put("HZB","HZCB");
        bankCodeMap.put("JSB","BOJS");
        bankCodeMap.put("ZSB","CZSB");
        bankCodeMap.put("BCOM","BOCOM");
        bankCodeMap.put("BOB","BCCB");
    }

    private String unBankCode(String bankCode){

        String unCode=bankCodeMap.get(bankCode);
        if(unCode!=null){
            return unCode;
        }
        return bankCode;

    }
}
