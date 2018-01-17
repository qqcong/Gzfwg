package com.bwoil.pay.channel.weixin.response;




import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;

import java.math.BigDecimal;

/**
 * 微信支付回调
 * Created by daoxing on 2016/9/23.
 */
public class Notify extends WxpayResponseBase {

    public static final String KEY_APPID                = "appid";
    public static final String KEY_MCH_ID               = "mch_id";
    public static final String KEY_OPENID               = "openid";
    public static final String KEY_TRANSACTION_ID       = "transaction_id";
    public static final String KEY_OUT_TRADE_NO         = "out_trade_no";
    public static final String KEY_TOTAL_FEE            = "total_fee";
    public static final String KEY_CASH_FEE             = "cash_fee";
    public static final String KEY_COUPON_FEE           = "coupon_fee";
    public static final String KEY_COUPON_COUNT         = "coupon_count";
    public Notify(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }

    // PROPERTY
    // ID
    public final String getOpenid()
    {
        return super.getProperty(KEY_OPENID);
    }

    public final String getOutTradeNo()
    {
        return super.getProperty(KEY_OUT_TRADE_NO);
    }

    public final String getTransactionId()
    {
        return super.getProperty(KEY_TRANSACTION_ID);
    }

    // FEE
    public final Integer getTotalFee()
    {
        return super.getIntProperty(KEY_TOTAL_FEE);
    }

    public final Integer getTotalFeeFen()
    {
        return this.getTotalFee();
    }

    public final BigDecimal getTotalFeeYuan()
    {
        Integer fen = this.getTotalFee();
        return fen!=null ? (new BigDecimal(fen).divide(new BigDecimal(100))) : null;
    }

    public final Integer getCashFee()
    {
        return super.getIntProperty(KEY_CASH_FEE);
    }

    public final Integer getCashFeeFen()
    {
        return this.getCashFee();
    }

    public final BigDecimal getCashFeeYuan()
    {
        Integer fen = this.getCashFee();
        return fen!=null ? (new BigDecimal(fen).divide(new BigDecimal(100))) : null;
    }

    public final Integer getCouponFee()
    {
        return super.getIntProperty(KEY_COUPON_FEE);
    }

    public final Integer getCouponFeeFen()
    {
        return this.getCouponFee();
    }

    public final BigDecimal getCouponFeeYuan()
    {
        Integer fen = this.getCouponFee();
        return    fen!=null ? (new BigDecimal(fen).divide(new BigDecimal(100))) : null;
    }

    // COUPON
    public final Integer getCouponCount()
    {
        return  this.getIntProperty(KEY_COUPON_COUNT)
        ;
    }
}
