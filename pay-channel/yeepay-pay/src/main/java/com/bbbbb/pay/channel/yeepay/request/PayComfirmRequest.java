package com.bbbbb.pay.channel.yeepay.request;

import com.bbbbb.pay.channel.yeepay.response.PayResponse;
import com.bbbbb.pay.common.pay.PayException;

import java.util.Map;

public class PayComfirmRequest extends  BaseRequest {
    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/bindpay/confirm";
    public PayComfirmRequest(Map<String, String> config) {
        super(config);
    }


    @Override
    public PayResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new PayResponse(json);
    }

    public PayComfirmRequest setRequestNo(String requestno){
        setProperty("requestno",requestno);
        return this;
    }

    /**
     * 短信验证码
     * @param validatecode
     * @return
     */
    public PayComfirmRequest setValidateCode(String validatecode){
        setProperty("validatecode",validatecode);
        return this;
    }
}
