package com.bbbbb.pay.gateway.form.bind;

import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * 
 * 绑卡确认表单
 */
@Data
public class BindComfirmForm extends SignForm {
    @NotBlank
    private String requestNo;
    @NotBlank
    private String validateCode;

}
