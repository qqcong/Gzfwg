package com.bwoil.pay.channel.baofu.response;

import com.bwoil.pay.common.pay.PayException;

/**
 * @author chendx
 * 解绑响应
 */
public class UnBindResponse extends BaseResponse {
    public UnBindResponse(String responseString) throws PayException {
        super(responseString);
    }
}
