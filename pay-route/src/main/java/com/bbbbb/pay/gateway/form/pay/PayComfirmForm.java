package com.bbbbb.pay.gateway.form.pay;

import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * 
 * 支付确认
 */
@Data
public class PayComfirmForm extends SignForm {

    /**
     * 提交支付订单时返回的交易编号
     */
    @NotBlank
    private String transId;

    /**
     * 短信验证码
     */
    @NotBlank
    private String validateCode;

}
