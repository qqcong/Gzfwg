package com.bwoil.pay.channel.baofu.response;

import com.bwoil.pay.common.pay.PayException;

/**
 * @author chendx
 * 预绑卡
 */
public class PreBindResponse extends  BaseResponse{
    public PreBindResponse(String responseString) throws PayException {
        super(responseString);
    }
}
