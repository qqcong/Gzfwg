package com.bbbbb.pay.channel.weixin.exception;

/**
 * 与微信服务交互时异常
 * 
 * @author 陈道兴
 * @since 2.0
 */
public class WechatRunTimeException extends Exception {

    private static final long serialVersionUID = 7300175978685072703L;

    public WechatRunTimeException() {
        super();
    }

    public WechatRunTimeException(String msg, Throwable e) {
        super(msg, e);
    }

    public WechatRunTimeException(String e) {
        super(e);
    }
    public WechatRunTimeException(String code,String msg) {
        super(msg);
        errCode=code;
    }
    public WechatRunTimeException(Throwable e) {
        super(e);
    }
    private String errCode;
    public String getErrCode(){
        return errCode;
    }

}
