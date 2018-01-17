package com.bwoil.pay.channel.yeepay.request;



import com.bwoil.pay.channel.yeepay.response.PayResponse;
import com.bwoil.pay.common.pay.PayException;

import java.util.Map;

public class QueryPayRequest  extends  BaseRequest{
    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/bindpay/record";
    public QueryPayRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    public PayResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new PayResponse(json);
    }


    public QueryPayRequest setRequestNo(String requestNo){
        setProperty("requestno",requestNo);
        return this;
    }

    public QueryPayRequest setOrderId(String yborderid){
        setProperty("yborderid",yborderid);
        return this;
    }
}
