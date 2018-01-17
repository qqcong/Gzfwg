package com.bwoil.pay.channel.baofu.response;

import com.bwoil.pay.common.pay.PayException;

/**
 * @author chendx
 * 查询退款响应
 */
public class QueryRefundResponse extends BaseResponse {
    public QueryRefundResponse(String responseString) throws PayException {
        super(responseString);
    }
    
    

    /**
     * 退款商户订单号
     * @return
     */
    public String getRefundOrderNo(){
        return (String)map.get("refund_order_no");
    }
    
    /**
     * 退款金额
     * @return
     */
    public String getRefundAmt(){
    	return (String)map.get("refund_amt");
    }
}
