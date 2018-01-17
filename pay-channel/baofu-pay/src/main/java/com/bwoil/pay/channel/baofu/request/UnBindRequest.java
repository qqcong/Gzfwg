package com.bwoil.pay.channel.baofu.request;


import com.bwoil.pay.channel.baofu.response.BaseResponse;
import com.bwoil.pay.channel.baofu.response.PreBindResponse;
import com.bwoil.pay.common.pay.PayException;

import java.util.Map;

/**
 * @author chendx
 * 解绑请求
 */
public class UnBindRequest extends BaseRequest {
    public UnBindRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    protected String getTxnSubType() {
        return "02";
    }

    @Override
    public BaseResponse execute() throws PayException {
        String responseString=post();
        return new PreBindResponse(responseString);

    }

    public UnBindRequest setBindId(String bindId){
        this.bodyParam.put("bind_id",bindId);
        return this;
    }
}
