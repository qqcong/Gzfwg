package com.bbbbb.pay.gateway.form.bank;

import com.bbbbb.common.framework.validation.BankCard;
import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

/**
 *
 * 银行卡信息表单
 */
@Data
public class CardForm extends SignForm{

    @NotBlank(message = "银行卡号不能为空")
    @BankCard
    private String cardNo;


}
