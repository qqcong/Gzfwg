package com.bwoil.pay.channel.baofu.response;

import com.bwoil.pay.common.pay.PayException;

/**
 * @author chendx
 * 支付响应
 */

public class PayResponse extends BaseResponse {
    public PayResponse(String responseString) throws PayException {
        super(responseString);
    }
    /**
     * 宝付业务流水号
     * @return
     */
    public String getBusinessNo(){
        return (String)map.get("business_no");
    }

    /**
     * 交易成功后返回的金额
     * @return
     */
    public int getSuccessAmount(){
        return Integer.parseInt(map.get("succ_amt").toString());
    }


}
