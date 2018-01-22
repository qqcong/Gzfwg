package com.bbbbb.pay.gateway.form.bank;

import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * 
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
