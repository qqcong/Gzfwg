package com.bbbbb.pay.gateway.form;

import com.bbbbb.common.framework.validation.NotBlank;
import lombok.Data;

/**
 * 
 * 需要签名验证的form
 */
@Data
public class SignForm {

    @NotBlank(message = "appid不能为空")
    private String appid;
    @NotBlank(message = "签名sign不能为空")
    private String sign;

    /**
     * 请求时间
     */
    @NotBlank
    private String timestamp;

    /**
     * 验证签名是否有效
     * @param key
     * @return
     */
    public boolean checkSign(String key){
        return true;
    }
}
