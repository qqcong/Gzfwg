package com.bwoil.pay.channel.yeepay.response;

import com.bwoil.pay.common.pay.PayException;

public class QueryCarResponse extends BaseResponse {
    public QueryCarResponse(String responseString) throws PayException {
        super(responseString);
    }

    public String getBankCode() {
        return map.get("bankcode");
    }

    public String getBankName() {
        return map.get("bankname");
    }

    public String getCardType() {
        return map.get("cardtype");
    }

    public String getValid() {
        return map.get("isvalid");
    }
}
