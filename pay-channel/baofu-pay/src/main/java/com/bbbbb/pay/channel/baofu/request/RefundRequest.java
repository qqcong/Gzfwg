package com.bbbbb.pay.channel.baofu.request;


import com.bbbbb.pay.channel.baofu.response.BaseResponse;
import com.bbbbb.pay.channel.baofu.response.PreBindResponse;
import com.bbbbb.pay.channel.baofu.response.RefundResponse;
import com.bbbbb.pay.common.pay.PayException;

import java.util.Map;

/**
 * 
 * 退款请求
 */
public class RefundRequest extends BaseRequest {
    public RefundRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    protected String getTxnSubType() {
        return "09";
    }

    @Override
    public BaseResponse execute() throws PayException {
        String responseString=post();
        return new RefundResponse(responseString);

    }

    /**
     * 退款类型
     * @param refundType
     * @return
     */
    public RefundRequest setRefundType(int refundType){
        this.bodyParam.put("refund_type",refundType);
        return this;
    }

    /**
     * 退款编号
     * @param transId
     * @return
     */
    public RefundRequest setTransId(String transId){
    	this.bodyParam.put("trans_id",transId);
    	return this;
    }

    /**
     * 退款订单号
     * @param refundOrderNo
     * @return
     */
    public RefundRequest setRefundOrderNo(String refundOrderNo){
    	this.bodyParam.put("refund_order_no",refundOrderNo);
    	return this;
    }

    /**
     * 退款原因
     * @param refundReason
     * @return
     */
    public RefundRequest setRefundReason(String refundReason){
    	this.bodyParam.put("refund_reason",refundReason);
    	return this;
    }

    /**
     * 退款金额(分)
     * @param refundAmt
     * @return
     */
    public RefundRequest setRefundAmt(int refundAmt){
    	this.bodyParam.put("refund_amt",refundAmt);
    	return this;
    }

    /**
     * 退款时间
     * @param refundTime
     * @return
     */
    public RefundRequest setRefundTime(String refundTime){
    	this.bodyParam.put("refund_time",refundTime);
    	return this;
    }

    /**
     * 退款回调地址
     * @param noticeUrl
     * @return
     */
    public RefundRequest setNoticeUrl(String noticeUrl){
    	this.bodyParam.put("notice_url",noticeUrl);
    	return this;
    }
   
}
