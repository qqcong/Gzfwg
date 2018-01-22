package com.bbbbb.pay.common.constants;

/**
 * 
 * 支付渠道
 *  on 2016/9/20.
 */
public enum RefundType {

	
	/*1:宝付收银台
	2:认证支付、代扣、快捷支付
	3:微信支付
	5:支付宝支付*/
    BAOFU_PAY(1,"宝付收银台"),
	AUTH_PAY(2,"认证支付、代扣、快捷支付"),
	WEIXIN_PAY(3,"微信支付"),
	ALIPAY(5,"支付宝支付");



    private int code;
    private String channelName;

    RefundType(int code,String channelName)
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

    public final static RefundType valueFromCode(int code){
        for(RefundType c:RefundType.values()){
            if(c.getCode()==code){
                return c;
            }
        }
        return null;
    }
}
