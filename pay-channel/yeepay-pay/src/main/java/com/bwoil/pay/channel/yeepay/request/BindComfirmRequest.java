package com.bwoil.pay.channel.yeepay.request;


import com.bwoil.pay.channel.yeepay.response.BindResponse;
import com.bwoil.pay.common.pay.PayException;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Map;

/**
 * @author chendx
 * 绑卡确认
 */
@CommonsLog
public class BindComfirmRequest extends BaseRequest {
    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/bindcard/confirm";
    public BindComfirmRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    public BindResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new BindResponse(json);
    }

    /**
     * 设置交易号
     * @param requestNo
     * @return
     */
    public BindComfirmRequest setRequestNo(String requestNo){
        setProperty("requestno",requestNo);
        return this;
    }

    /**
     * 短信验证码
     * @param validatecode
     * @return
     */
    public BindComfirmRequest setvVlidateCode(String validatecode){
        setProperty("validatecode",validatecode);
        return this;
    }

}
