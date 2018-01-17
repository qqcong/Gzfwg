package com.bwoil.pay.gateway.form.bank;

import com.bwoil.common.framework.validation.NotBlank;
import com.bwoil.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * @author chendx
 * 可用银行卡列表
 */
@Data
public class CardListForm extends SignForm {
    /**
     * 用户id
     */
    @NotBlank
    private String userId;
    /**
     * 支付金额
     */
    private String amount;
}
