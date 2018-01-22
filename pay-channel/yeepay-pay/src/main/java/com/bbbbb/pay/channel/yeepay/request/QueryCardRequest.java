package com.bbbbb.pay.channel.yeepay.request;

import com.bbbbb.pay.channel.yeepay.response.QueryCarResponse;
import com.bbbbb.pay.common.pay.PayException;

import java.util.Map;

public class QueryCardRequest  extends BaseRequest{
    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/bankcard/check";
    public QueryCardRequest(Map<String, String> config) {

    super(config);
    }

    public QueryCardRequest setCardNo(String cardNo){
        this.setProperty("cardno",cardNo);
        return this;
    }
    @Override
    public QueryCarResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new QueryCarResponse(json);
    }
}
