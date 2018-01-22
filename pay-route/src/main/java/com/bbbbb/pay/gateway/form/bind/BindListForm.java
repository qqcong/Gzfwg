package com.bbbbb.pay.gateway.form.bind;

import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * 
 * 已绑卡列表
 */
@Data
public class BindListForm extends SignForm{
    /**
     * 用户id
     */
    private String userId;

    /**
     * 需支付金额
     */
    private String amount;
}
