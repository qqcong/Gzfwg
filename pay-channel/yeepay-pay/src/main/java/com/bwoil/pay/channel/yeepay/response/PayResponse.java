package com.bwoil.pay.channel.yeepay.response;

import com.bwoil.pay.common.constants.TradeStatus;
import com.bwoil.pay.common.pay.PayException;

import java.math.BigDecimal;

public class PayResponse extends BaseResponse {
    public PayResponse(String responseString) throws PayException {
        super(responseString);
    }
    /**
     * 业务流水号
     * @return
     */
    public String getBusinessNo(){
        return map.get("yborderid");
    }

    /**
     * 交易成功后返回的金额
     * @return
     */
    public int getSuccessAmount(){
        return new BigDecimal(map.get("amount")).multiply(new BigDecimal(100)).intValue();

    }
    
    /**
     * 获取绑定id
     * @return
     */
    public String getBindId(){
        return getCardTop()+"_"+getCardLast();
    }
    
    public String getCardTop(){
        return map.get("cardtop");
    }
    public String getCardLast(){
        return map.get("cardlast");
    }

    public TradeStatus getTradeStatus(){
       String status= map.get("status");
       if("ACCEPT".equals(status)){
           return TradeStatus.PROCESSING;
       }else  if("TO_VALIDATE".equals(status)){
           return TradeStatus.WAIT;
       }else  if("PROCESSING".equals(status)){
           return TradeStatus.PROCESSING;
       }else  if("PAY_FAIL".equals(status)){
           return TradeStatus.ERROR;
       }else  if("PAY_SUCCESS".equals(status)){
           return TradeStatus.SUCCESS;
       }else  if("TIME_OUT".equals(status)){
           return TradeStatus.ERROR;
       }else  if("FAIL".equals(status)){
           return TradeStatus.FAIL;
       }else{
           return TradeStatus.WAIT;
       }
    }
}
