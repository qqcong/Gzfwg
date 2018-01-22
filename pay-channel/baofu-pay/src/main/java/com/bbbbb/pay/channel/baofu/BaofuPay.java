package com.bbbbb.pay.channel.baofu;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bbbbb.pay.channel.baofu.request.PayComfirmRequest;
import com.bbbbb.pay.channel.baofu.request.PrePayRequest;
import com.bbbbb.pay.channel.baofu.request.QueryPayRequest;
import com.bbbbb.pay.channel.baofu.response.PayResponse;
import com.bbbbb.pay.channel.baofu.response.PrePayResponse;
import com.bbbbb.pay.channel.baofu.response.QueryPayResponse;
import com.bbbbb.pay.common.constants.TradeStatus;
import com.bbbbb.pay.common.pay.FastPay;
import com.bbbbb.pay.common.pay.PayException;
import com.bbbbb.pay.common.pay.SystemException;
import com.bbbbb.pay.common.pay.form.FirstPayForm;
import com.bbbbb.pay.common.pay.form.PayForm;
import com.bbbbb.pay.common.pay.result.BindResult;
import com.bbbbb.pay.common.pay.result.PayResult;

import lombok.extern.apachecommons.CommonsLog;

/**
 * 
 * 宝付投资通快捷支付
 */
@CommonsLog
public class BaofuPay implements FastPay {
    private Map<String,String> config;

    @Override
    public void setConfig(Map<String, String> config) {
        this.config=config;
    }


    /**
     * 需要代查询的错误
     */
    private static final List<String> REQUERY_ERROR= Arrays.asList("BW00100","BW00112","BW00113","BW00115","BW00144","BW00202");

    @Transactional
    @Override
    public PayResult pay(PayForm payForm) {

        PayResult result=new PayResult();
        PrePayRequest request=new PrePayRequest(config);
        if(StringUtils.isBlank(payForm.getIp())) {
            payForm.setIp("127.0.0.1");
        }
        request.setBindId(payForm.getAccount()).setClientIp(payForm.getIp()).setTransId(payForm.getOrderNo()).setTxnAmt(payForm.getAmount());
        result.setPaid(false);
        result.setPayAmount(payForm.getAmount());
        result.setTradeStatus(TradeStatus.WAIT);
        result.setOrderNo(payForm.getOrderNo());
        try {
            PrePayResponse response= request.execute();
            result.setTransactionNo(response.getBusinessNo());
        }catch (PayException e){

            if(REQUERY_ERROR.contains(e.getCode())){
                result.setTradeStatus(TradeStatus.FAIL);
            }else{
                result.setTradeStatus(TradeStatus.ERROR);
            }
            result.setErrCode(e.getCode());
            result.setErrMsg(e.getMessage());

            log.error("预支付订单失败",e);
        }


        return result;
    }
    @Transactional
    @Override
    public PayResult comfirm(String transactionNo, String smsCode) {
        PayResult result=new PayResult();
        PayComfirmRequest request=new PayComfirmRequest(config);
        request.setBusinessNo(transactionNo).setSmsCode(smsCode);
        result.setPaid(false);
        try {
            PayResponse response= request.execute();
            result.setTransactionNo(response.getBusinessNo());
            result.setPaid(true);
            result.setTimePaid(response.getTradeDate());
            result.setTradeStatus(TradeStatus.SUCCESS);
            result.setPayAmount(response.getSuccessAmount());
        }catch (PayException e){

            if(REQUERY_ERROR.contains(e.getCode())){
                result.setTradeStatus(TradeStatus.FAIL);
            }else{
                result.setTradeStatus(TradeStatus.ERROR);
            }
            result.setErrCode(e.getCode());
            result.setErrMsg(e.getMessage());
            log.error("预支付订单失败",e);
        }


        return result;
    }
    @Transactional
    @Override
    public PayResult firstPay(FirstPayForm form) {
        PayResult result=new PayResult();
        BaofuBindCard bindCard=new BaofuBindCard();
        bindCard.setConfig(config);
        BindResult bindResult= bindCard.directBind(form.getCardInfo());
        /*
         *绑卡成功
         */
        if(bindResult.isSuccess()){
            /*
             *调用预付接口
             */
            result=this.pay(form.getPayInfo());
            form.getPayInfo().setAccount(bindResult.getBindId());
            result.setBindId(bindResult.getBindId());
        }else{
            result.setTradeStatus(TradeStatus.ERROR);
            result.setPaid(false);
            result.setErrCode(bindResult.getErrCode());
            result.setErrMsg(bindResult.getErrMsg());
        }
        return result;
    }


    @Override
    public PayResult query(String orderId, String transactionNo) {

        PayResult result=new PayResult();
        QueryPayRequest request=new QueryPayRequest(config);
        request.setOrigTransId(transactionNo);
        request.setOrigTradeDate(new Date());
        try {
            QueryPayResponse response=   request.execute();
            result.setTradeStatus(response.getTradeStatus());
            result.setPayAmount(response.getSuccessAmount());
            result.setTransactionNo(response.getTransNo());
        }catch (PayException e){
            if(REQUERY_ERROR.contains(e.getCode())){
                result.setTradeStatus(TradeStatus.FAIL);
            }else{
                result.setTradeStatus(TradeStatus.ERROR);
            }
            result.setErrCode(e.getCode());
            result.setErrMsg(e.getMessage());
        }

        return result;
    }

    @Override
    public void checkConfig(Map<String, String> config) throws SystemException {

    }
    
    @Override
	public boolean isSupportFirstPay() {		
		return false;
	}
}
