package com.bwoil.pay.channel.baofu.response;

import com.bwoil.pay.common.pay.PayException;

/**
 * @author chendx
 * 退款请求响应
 */
public class RefundResponse extends BaseResponse {
    public RefundResponse(String responseString) throws PayException {
        super(responseString);
    }
    
    
    /**
     * 退款宝付业务流水号
     * @return
     */
    public String getRefundBusinessNo(){
        return (String)map.get("refund_business_no");
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
