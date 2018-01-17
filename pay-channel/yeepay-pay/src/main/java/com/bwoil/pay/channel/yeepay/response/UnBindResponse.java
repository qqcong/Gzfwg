package com.bwoil.pay.channel.yeepay.response;

import com.bwoil.pay.common.pay.PayException;

public class UnBindResponse extends BaseResponse {
    public UnBindResponse(String responseString) throws PayException {
        super(responseString);
    }
}
