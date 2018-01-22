package com.bbbbb.pay.gateway.form.bind;

import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * 
 * 查询绑卡情况
 */
@Data
public class QueryBindForm extends SignForm {
    /**
     * 用户id
     */
    @NotBlank
    private String userId;
    /**
     *卡号
     */
    private String cardNo;
    /**
     * 绑卡ID
     */
    private String bindId;
}
