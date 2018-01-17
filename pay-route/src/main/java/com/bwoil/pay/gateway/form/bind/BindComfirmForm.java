package com.bwoil.pay.gateway.form.bind;

import com.bwoil.common.framework.validation.NotBlank;
import com.bwoil.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * @author chendx
 * 绑卡确认表单
 */
@Data
public class BindComfirmForm extends SignForm {
    @NotBlank
    private String requestNo;
    @NotBlank
    private String validateCode;

}
