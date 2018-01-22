package com.bbbbb.pay.gateway.result.pay;

import com.bbbbb.pay.gateway.result.SignResult;
import lombok.Data;

/**
 * 支付定单号
 */
@Data
public class PayOrderInfo extends SignResult{

    private String status;//	string	SUCCESS FAIL ERROR WAIT
    private String amount;//	string	实付金额
    private String orderNo;//   商户订单号
    private String transId;//	string	支付系统交易流水号
    private String bindId;//	string	绑卡编号
    private String channel;//
    private String bankCode;
}
