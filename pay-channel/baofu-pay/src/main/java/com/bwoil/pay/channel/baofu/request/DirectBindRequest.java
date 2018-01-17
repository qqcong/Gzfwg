package com.bwoil.pay.channel.baofu.request;

import com.bwoil.pay.channel.baofu.response.BindResponse;
import com.bwoil.pay.common.pay.PayException;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Map;

/**
 * 直接绑卡，无需要验证
 */
@CommonsLog
public class DirectBindRequest extends PreBindRequest {
    public DirectBindRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    protected String getTxnSubType() {
        return "01";
    }

    @Override
    public BindResponse execute() throws PayException {
        String responseString=post();
        return new BindResponse(responseString);
    }
}
