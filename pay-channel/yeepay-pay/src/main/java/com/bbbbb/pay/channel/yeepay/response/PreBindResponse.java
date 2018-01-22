package com.bbbbb.pay.channel.yeepay.response;

import com.bbbbb.pay.common.pay.PayException;

/**
 * @author chendx
 * 预绑卡
 */
public class PreBindResponse extends  BaseResponse{
    public PreBindResponse(String responseString) throws PayException {
        super(responseString);
    }
    public String getRequestNo(){
        return map.get("requestno");
    }
    public String getOrderId(){
        return map.get("yborderid");
    }
    public String getStatus(){
        return map.get("status");
    }
    public String getSmsCode(){
        return map.get("smscode");
    }
    public String getCodeSender(){
        return map.get("codesender");
    }
    public String getEnhancedType(){
        return map.get("enhancedtype");
    }
    public String getSmsType(){
        return map.get("smstype");
    }


}
