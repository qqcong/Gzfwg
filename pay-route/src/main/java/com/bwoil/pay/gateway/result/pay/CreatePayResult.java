package com.bwoil.pay.gateway.result.pay;

import com.bwoil.pay.gateway.result.SignResult;
import lombok.Data;

import java.util.Map;

@Data
public class CreatePayResult extends SignResult {

    /**
     * 渠道名称
     */
    private String channel;

    /**
     * 支付系统单号
     */
    private String transactionId;
    /**
     * 前端支付所需参数(凭证)
     * 不同渠道，返回的参数是不一样的
     */
    private Map<String,String> credential;
}
