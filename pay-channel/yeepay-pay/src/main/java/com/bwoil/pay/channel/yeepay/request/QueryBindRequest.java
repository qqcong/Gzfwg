package com.bwoil.pay.channel.yeepay.request;


import com.bwoil.pay.channel.yeepay.response.BindResponse;
import com.bwoil.pay.common.pay.PayException;

import java.util.Map;

/**
 *
 */
public class QueryBindRequest extends BaseRequest {

    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/bindcard/record";
    public QueryBindRequest(Map<String, String> config) {
        super(config);
    }



    @Override
    public BindResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new BindResponse(json);

    }

    public QueryBindRequest setRequestNo(String requestNo){
        setProperty("requestno",requestNo);
        return this;
    }

    public QueryBindRequest setOrderId(String yborderid){
        setProperty("yborderid",yborderid);
        return this;
    }
}
