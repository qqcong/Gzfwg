package com.bwoil.pay.channel.baofu.response;

import com.bwoil.pay.common.pay.PayException;

/**
 * @author chendx
 * 查询绑定响应
 */
public class QueryBindResponse extends BaseResponse {

    public QueryBindResponse(String responseString) throws PayException {
        super(responseString);
    }
}
