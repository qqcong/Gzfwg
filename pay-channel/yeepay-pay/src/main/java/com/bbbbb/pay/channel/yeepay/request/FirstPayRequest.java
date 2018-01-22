package com.bbbbb.pay.channel.yeepay.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.bbbbb.common.framework.util.DateUtils;
import com.bbbbb.pay.channel.yeepay.response.PayResponse;
import com.bbbbb.pay.common.pay.PayException;

public class FirstPayRequest  extends BaseRequest{
    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/firstpay/request";
    public FirstPayRequest(Map<String, String> config) {

    super(config);
    }    
    
    @Override
    public PayResponse execute() throws PayException {
    	setProperty("idcardtype","ID");
    	setProperty("requesttime", DateUtils.format(new Date()));
    	dataMap.put("registtime", DateUtils.format(new Date()));
    	dataMap.put("issetpaypwd", "0");
    	dataMap.put("terminalid", "1610000449592");
    	
        String json=this.execute(REQ_URL);
        return new PayResponse(json);
    }
    
    public void setCardNo(String cardNo){
        this.setProperty("cardno",cardNo);
    }
    

    
    /**
     * 设置交易号
     * @param requestno
     * @return
     */
    public void setResquestNo(String requestno){
        setProperty("requestno",requestno);        
    }
    
    public void setIdCard(String idCard) {
    	setProperty("idcardno", idCard);   
    }

    public void setUserName(String name) {
    	setProperty("username", name);   
    }    
    
    public void setMobile(String mobile) {
    	setProperty("phone",  mobile);   
    }
    
    /**
     * 支付金额
     * @param amount 金额以分为单位(整型数据)并把元转换成分
     * @return
     */
    public void setAmount(String amount){
    	Integer am = Integer.valueOf(amount);
        setProperty("amount",new BigDecimal(am).multiply(new BigDecimal(100)).setScale(2).toString());        
    }
    
    public void setAmount(BigDecimal amount){
        setProperty("amount",amount.setScale(2).toString());
        
    }

    public void setProductName(String productname){
        setProperty("productname",productname);        
    }
    
    public void setRegistTime(Date registtime){
        setProperty("registtime", DateUtils.format(registtime));        
    }
    
    public void setLastLoginTerminalId(String lastLoginTerminalId){
        setProperty("lastloginterminalid", lastLoginTerminalId);
        
    }
    
    public void setCallBackUrl(String url){
        setProperty("callbackurl", url);        
    }
}
