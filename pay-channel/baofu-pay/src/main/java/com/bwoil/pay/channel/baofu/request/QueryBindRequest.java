package com.bwoil.pay.channel.baofu.request;


import com.bwoil.pay.channel.baofu.response.BindResponse;
import com.bwoil.pay.common.pay.PayException;

import java.util.Map;

/**
 *@author chendx
 * 查询绑卡信息请求
 */
public class QueryBindRequest extends BaseRequest {
    public QueryBindRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    protected String getTxnSubType() {
        return "03";
    }

    @Override
    public BindResponse execute() throws PayException {
        String responseString=post();
        return new BindResponse(responseString);

    }

    /**
     * 卡号
     * @param accNo
     * @return
     */
    public QueryBindRequest setAccNo(String accNo){
        this.bodyParam.put("acc_no",accNo);
        return this;
    }
}
