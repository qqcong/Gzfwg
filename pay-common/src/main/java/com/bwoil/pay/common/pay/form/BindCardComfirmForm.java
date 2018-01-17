package com.bwoil.pay.common.pay.form;

import lombok.Data;

/**
 * @author chendx
 * 绑卡确认
 */
@Data
public class BindCardComfirmForm {

    /**
     * 短信验证码
     */
    private String validateCode;

    /**
     * 绑卡请求编号
     */
    private String requestNo;
}
