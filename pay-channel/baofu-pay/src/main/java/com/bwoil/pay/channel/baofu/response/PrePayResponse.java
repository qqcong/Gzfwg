package com.bwoil.pay.channel.baofu.response;

import com.bwoil.pay.common.pay.PayException;

/**
 * @author chendx
 *预支付请求响应
 */
public class PrePayResponse extends BaseResponse {
    public PrePayResponse(String responseString) throws PayException {
        super(responseString);
    }

    /**
     * 商户交易号
     * @return
     */
    @Override
    public String getTransId(){
        return (String)map.get("trans_id");
    }

    /**
     * 宝付业务流水号
     * @return
     */
    public String getBusinessNo(){
        return (String)map.get("business_no");
    }

}
