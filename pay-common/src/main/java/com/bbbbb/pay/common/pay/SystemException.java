package com.bbbbb.pay.common.pay;

import com.bbbbb.pay.common.util.ErrorsUtils;
import lombok.Data;
import lombok.Getter;
/**
 * 
 * 我方系统异常，比如网络中断，证书不正确等
 */
@Data
public class SystemException extends RuntimeException{

    @Getter
    private String code;

    public SystemException(String code) {

        this.code = code;

    }
    public SystemException(String code,String message) {
        super(message);
        this.code = code;
    }
    public SystemException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
