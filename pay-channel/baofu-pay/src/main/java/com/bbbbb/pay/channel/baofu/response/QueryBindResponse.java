package com.bbbbb.pay.channel.baofu.response;

import com.bbbbb.pay.common.pay.PayException;

/**
 * 
 * 查询绑定响应
 */
public class QueryBindResponse extends BaseResponse {

    public QueryBindResponse(String responseString) throws PayException {
        super(responseString);
    }
}
