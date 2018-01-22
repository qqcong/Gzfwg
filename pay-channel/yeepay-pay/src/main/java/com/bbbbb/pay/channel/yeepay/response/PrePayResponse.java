package com.bbbbb.pay.channel.yeepay.response;

import com.bbbbb.pay.common.pay.PayException;

public class PrePayResponse extends BaseResponse {
    public PrePayResponse(String responseString) throws PayException {
        super(responseString);
    }

    public String getRequestNo(){
        return map.get("requestno");
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
    public String getPhone(){
        return map.get("phone");
    }
    public String getSmsType(){
        return map.get("smstype");
    }

}
