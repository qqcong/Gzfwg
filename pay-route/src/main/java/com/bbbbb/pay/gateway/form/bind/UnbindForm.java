package com.bbbbb.pay.gateway.form.bind;

import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * 
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
