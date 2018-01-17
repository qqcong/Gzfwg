package com.bwoil.pay.channel.weixin.exception;

/**
 * 微信高级API请求异常
 * 
 * @author 陈道兴
 * @since 2.0
 */
public class WechatApiException extends Exception {

    private static final long serialVersionUID = -303278319021435258L;

    public WechatApiException() {
        super();
    }

    public WechatApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatApiException(String message) {
        super(message);
    }

    public WechatApiException(Throwable cause) {
        super(cause);
    }

    public WechatApiException(String code,String msg) {

        super(msg);
        errCode=code;

    }
    private String errCode;
    public String getErrCode(){
        return errCode;
    }

}
