package com.bwoil.pay.gateway.form.pay;

import com.bwoil.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * 查询支付订单信息和状态
 */
@Data
public class QueryPayForm extends SignForm{
    private String requestNo;
    private String transId;
}
