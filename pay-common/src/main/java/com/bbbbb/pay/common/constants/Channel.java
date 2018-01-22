package com.bbbbb.pay.common.constants;

/**
 * 
 * 支付渠道
 * Created by daoxing on 2016/9/20.
 */
public enum Channel {

    /**
     * 微信支付，即微信 APP 支付
     */
    WX_APP(1,"wx"),
    /**
     * 微信公众号支付
     */
    WX_PUB(2,"wx_pub"),
    /**
     * 微信公众号扫码支付
     */
    WX_PUB_QR(3,"wx_qr"),
    /**
     * 微信H5支付
     */
    WX_WAP(4,"wx_h5"),

    /**
     * 网关支付
     */
    GATEWQY(5,"gateway"),

    /**
     * 快捷支付
     */
    FASTPAY(6,"fast"),

    BAOFU(8,"baofu"),

    YEEPAY(9,"yeepay"),
    /**
     * Apple Pay
     */
    APPLE_PAY(7,"ApplePay");



    private int code;
    private String channelName;

    Channel(int code,String channelName)
    {
        this.code = code;
        this.channelName=channelName;
    }
    public int getCode(){
        return this.code;
    }

    public String getChannelName() {
        return channelName;
    }

    public final static Channel valueFromCode(int code){
        for(Channel c:Channel.values()){
            if(c.getCode()==code){
                return c;
            }
        }
        return null;
    }
}
