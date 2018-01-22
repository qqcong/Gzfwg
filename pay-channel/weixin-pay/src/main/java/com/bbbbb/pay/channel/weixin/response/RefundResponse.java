package com.bbbbb.pay.channel.weixin.response;


import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;

/**
 * Created by daoxing on 2016/9/23.
 */
public class RefundResponse extends WxpayResponseBase {

    public static final String KEY_REFUND_ID            = "refund_id";
    public static final String KEY_REFUND_FEE           = "refund_fee";
    public static final String KEY_CASH_REFUND_FEE      = "cash_refund_fee";
    public static final String KEY_COUPON_REFUND_FEE    = "coupon_refund_fee";
    public static final String KEY_COUPON_REFUND_COUNT  = "coupon_refund_count";
    public RefundResponse(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }

    // PROPERTY
    public String getRefundId()
    {
        return(
                super.getProperty(KEY_REFUND_ID)
        );
    }

    /** @return refund_fee in CNY fen
     */
    public Integer getRefundFee()
    {
        return(
                super.getIntProperty(KEY_REFUND_FEE)
        );
    }

    /** @return coupon_refund_fee in CNY fen
     */
    public Integer getCashRefundFee()
    {
        return(
                super.getIntProperty(KEY_CASH_REFUND_FEE)
        );
    }

    /** @return coupon_refund_fee in CNY fen
     */
    public Integer getCouponRefundFee()
    {
        return(
                super.getIntProperty(KEY_COUPON_REFUND_FEE)
        );
    }

    public final Integer getCouponRefundCount()
    {
        return(
                super.getIntProperty(KEY_COUPON_REFUND_COUNT)
        );
    }

}
