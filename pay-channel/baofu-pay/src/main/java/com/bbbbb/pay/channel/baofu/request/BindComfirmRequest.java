package com.bbbbb.pay.channel.baofu.request;

import com.bbbbb.pay.channel.baofu.response.BindResponse;
import com.bbbbb.pay.channel.baofu.response.PreBindResponse;
import com.bbbbb.pay.common.pay.PayException;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Map;

/**
 * 
 * 绑卡确认
 */
@CommonsLog
public class BindComfirmRequest extends BaseRequest {

    public BindComfirmRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    public BindResponse execute() throws PayException {
        String responseString=post();
        return new BindResponse(responseString);
    }

    @Override
    protected String getTxnSubType() {
        return "12";
    }

    /**
     * 设置交易号
     * @param transId
     * @return
     */
    public BindComfirmRequest setTransId(String transId){
        this.bodyParam.put("trans_id",transId);
        return this;
    }

    /**
     * 短信验证码
     * @param smsCode
     * @return
     */
    public BindComfirmRequest setSmsCode(String smsCode){
        this.bodyParam.put("sms_code",smsCode);
        return this;
    }
}
