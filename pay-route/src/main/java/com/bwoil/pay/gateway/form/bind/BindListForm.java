package com.bwoil.pay.gateway.form.bind;

import com.bwoil.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * @author chendx
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
