package com.bwoil.pay.channel.baofu.request;
import com.bwoil.pay.channel.baofu.response.PayResponse;
import com.bwoil.pay.common.pay.PayException;

import java.util.Map;

/**
 * @author chendx
 * 支付确认请求
 */
public class PayComfirmRequest extends  BaseRequest {
    public PayComfirmRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    protected String getTxnSubType() {
        return "16";
    }

    @Override
    public PayResponse execute() throws PayException {
        String responseString=post();
        return new PayResponse(responseString);
    }

    public PayComfirmRequest setBusinessNo(String businessNo){
        this.bodyParam.put("business_no",businessNo);
        return this;
    }

    /**
     * 短信验证码
     * @param smsCode
     * @return
     */
    public PayComfirmRequest setSmsCode(String smsCode){
        this.bodyParam.put("sms_code",smsCode);
        return this;
    }
}
