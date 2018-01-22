package com.bbbbb.pay.common.pay;

import lombok.Data;
import lombok.Getter;

/**
 * 
 * 支付异常,由第三方系统产生，比如余额不足等
 */
@Data
public class PayException extends Exception {

    @Getter
    private String code;
    public PayException(String code,String message) {
        super(message);
        this.code = code;
    }
    public PayException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
