package com.bbbbb.pay.common.constants;

/**
 *  on 2016/9/29.
 */
public enum RefundStatus {
    NOTSURE("nosurer",0),//未确定，需要商户原退款单号重新发起
    PROCESSING("pending",1),//退款处理中
    SUCCESS("success",16),//退款成功
    FAIL("failed",-1),//退款失败
    CHANGE("change",-2);//转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。

    private String code;
    private int value;
    RefundStatus(String code,int value)
    {
        this.code = code;
        this.value= value;

    }
    public String getCode(){
        return this.code;
    }
    public  int getValue(){
        return this.value;
    }


}
