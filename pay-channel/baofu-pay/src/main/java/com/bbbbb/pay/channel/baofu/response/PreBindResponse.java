package com.bbbbb.pay.channel.baofu.response;

import com.bbbbb.pay.common.pay.PayException;

/**
 * 
 * 预绑卡
 */
public class PreBindResponse extends  BaseResponse{
    public PreBindResponse(String responseString) throws PayException {
        super(responseString);
    }
}
