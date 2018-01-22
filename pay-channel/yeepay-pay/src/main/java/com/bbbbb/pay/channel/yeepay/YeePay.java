package com.bbbbb.pay.channel.yeepay;


import java.util.Map;

import com.bbbbb.pay.channel.yeepay.request.FirstPayRequest;
import com.bbbbb.pay.channel.yeepay.request.FirstPaycomfirmRequest;
import com.bbbbb.pay.channel.yeepay.request.PayComfirmRequest;
import com.bbbbb.pay.channel.yeepay.request.PrePayRequest;
import com.bbbbb.pay.channel.yeepay.request.QueryPayRequest;
import com.bbbbb.pay.channel.yeepay.response.PayResponse;
import com.bbbbb.pay.channel.yeepay.response.PrePayResponse;
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
public class YeePay implements FastPay {
    private Map<String,String> config;

    private String backCallUrl="";
    @Override
    public void setConfig(Map<String, String> config) {
        this.config=config;
    }

    @Override
    public PayResult pay(PayForm payForm) {

        PayResult result=new PayResult();
        PrePayRequest request=new PrePayRequest(config);
        String account = payForm.getAccount();
        String cardLast = account.substring(account.length() - 4);
        
        request.setCardTop(account.substring(0,6));
        request.setCardLast(cardLast);
        request.setResquestNo(payForm.getOrderNo());
        request.setAmount(payForm.getAmount());
        request.setIdentityId(payForm.getIdentityId());
        request.setCallBackUrl(backCallUrl);
        result.setPaid(false);
        result.setPayAmount(payForm.getAmount());
        result.setTradeStatus(TradeStatus.WAIT);
        result.setOrderNo(payForm.getOrderNo());
        try {
            PrePayResponse response= request.execute();
            result.setTransactionNo(response.getRequestNo());
        }catch (PayException e){
            result.setErrCode(e.getCode());
            result.setErrMsg(e.getMessage());
            result.setTradeStatus(TradeStatus.ERROR);
            log.error("预支付订单失败",e);
        }


        return result;
    }
    @Override
    public PayResult comfirm(String transactionNo, String smsCode) {
        PayResult result=new PayResult();
        PayComfirmRequest request=new PayComfirmRequest(config);
        request.setRequestNo(transactionNo).setValidateCode(smsCode);
        result.setPaid(false);
        try {
            PayResponse response= request.execute();
            result.setTransactionNo(response.getBusinessNo());
            result.setPaid(response.getTradeStatus()==TradeStatus.SUCCESS);
            result.setTradeStatus(response.getTradeStatus());
        }catch (PayException e){
            result.setErrCode(e.getCode());
            result.setErrMsg(e.getMessage());
            log.error("预支付订单失败",e);
        }


        return result;
    }
    
    public PayResult firstPayOld(FirstPayForm form) {
        PayResult result=new PayResult();
        YeepayBindCard bindCard=new YeepayBindCard();
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
        }else{
            result.setTradeStatus(TradeStatus.ERROR);
            result.setPaid(false);
            result.setErrCode(bindResult.getErrCode());
            result.setErrMsg(bindResult.getErrMsg());
        }
        return result;
    }

    @Override
    public PayResult firstPay(FirstPayForm form) {
    	PayResult result=new PayResult();
    	FirstPayRequest request=new FirstPayRequest(config);
    	
    	request.setResquestNo(form.getOrderNo());
    	request.setIdentityId(form.getUserId());
    	request.setCardNo(form.getCardNo());
    	request.setIdCard(form.getIdCardNo());
    	request.setUserName(form.getUserName());
    	request.setMobile(form.getMobile());
    	
        request.setAmount(form.getAmount());
        request.setCallBackUrl(backCallUrl);                       
    	
        try {
            PayResponse response=   request.execute();
            result.setTradeStatus(response.getTradeStatus());
            result.setPaid(true);
            
        }catch (PayException e){
        	result.setPaid(false);
        	result.setErrCode(e.getCode());
        	result.setErrMsg(e.getMessage());
        } finally {
        	String cardNo = form.getCardNo();
            String cardTop = cardNo.substring(0, 6);
            String cardLast = cardNo.substring(cardNo.length() - 4);
            String bindId = cardTop + "_" + cardLast;
            result.setBindId(bindId);
        }

        return result;    	
    } 
    
//    @Override  TODO
    public PayResult firstPaycomfirm(String transactionNo, String smsCode) {
        PayResult result=new PayResult();
        FirstPaycomfirmRequest request=new FirstPaycomfirmRequest(config);
        request.setRequestNo(transactionNo).setValidateCode(smsCode);
        result.setPaid(false);
        try {
            PayResponse response= request.execute();
            result.setTransactionNo(response.getBusinessNo());
            result.setPaid(response.getTradeStatus()==TradeStatus.SUCCESS);
            result.setTradeStatus(response.getTradeStatus());
        }catch (PayException e){
            result.setErrCode(e.getCode());
            result.setErrMsg(e.getMessage());
            log.error("预支付订单失败",e);
        }


        return result;
    }
    
    @Override
    public PayResult query(String orderId, String transactionNo) {

        PayResult result=new PayResult();
        QueryPayRequest request=new QueryPayRequest(config);
        request.setOrderId(transactionNo);
        try {
            PayResponse response=   request.execute();
            result.setTradeStatus(response.getTradeStatus());
            result.setPayAmount(response.getSuccessAmount());
        }catch (PayException e){

        }

        return result;
    }

    /**
     * 检查配置信息
     * 当配置有误抛出系统异常
     * @throws SystemException
     */
    @Override
    public void checkConfig(Map<String, String> config) throws SystemException{

    }
    
    @Override
	public boolean isSupportFirstPay() {		
		return true;
	}
}
