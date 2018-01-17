package com.bwoil.pay.gateway.form.bank;

import com.bwoil.common.framework.validation.BankCard;
import com.bwoil.common.framework.validation.NotBlank;
import com.bwoil.pay.gateway.form.SignForm;
import lombok.Data;

/**
 *@author chendx
 * 银行卡信息表单
 */
@Data
public class CardForm extends SignForm{

    @NotBlank(message = "银行卡号不能为空")
    @BankCard
    private String cardNo;


}
