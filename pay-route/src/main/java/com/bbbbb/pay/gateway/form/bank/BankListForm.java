package com.bbbbb.pay.gateway.form.bank;

import com.bbbbb.common.framework.validation.Money;
import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

/**
 *  
 * 可用银行查询表单
 */
@Data
public class BankListForm extends SignForm{
    /**
     *  付款金额 单位:元
     */
    @Money
    private String  amount;
    /**
     *支付方式 1：快捷支付 2：网关支付 默认 1
     */
    private String  type;
}
