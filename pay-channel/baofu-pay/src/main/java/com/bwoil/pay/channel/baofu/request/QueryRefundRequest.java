package com.bwoil.pay.channel.baofu.request;


import com.bwoil.pay.channel.baofu.response.BaseResponse;
import com.bwoil.pay.channel.baofu.response.PreBindResponse;
import com.bwoil.pay.channel.baofu.response.QueryRefundResponse;
import com.bwoil.pay.channel.baofu.response.RefundResponse;
import com.bwoil.pay.common.pay.PayException;

import java.util.Map;

/**
 * @author chendx
 * 查询退款请求
 */
public class QueryRefundRequest extends BaseRequest {
    public QueryRefundRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    protected String getTxnSubType() {
        return "10";
    }

    @Override
    public BaseResponse execute() throws PayException {
        String responseString=post();
        return new QueryRefundResponse(responseString);

    }


    /**
     * 退款单号
     * @param refundOrderNo
     * @return
     */
    public QueryRefundRequest setRefundOrderNo(String refundOrderNo){
    	this.bodyParam.put("refund_order_no",refundOrderNo);
    	return this;
    }
   
}
