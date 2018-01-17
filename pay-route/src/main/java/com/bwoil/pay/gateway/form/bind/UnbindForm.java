package com.bwoil.pay.gateway.form.bind;

import com.bwoil.common.framework.validation.NotBlank;
import com.bwoil.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * @author chendx
 * 解绑请求
 */
@Data
public class UnbindForm extends SignForm {

    @NotBlank
    private String userId;
    private String channel;
    private String cardNo;
    private String bindId;
}
