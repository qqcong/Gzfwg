package com.bwoil.pay.channel.weixin.response;




import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.common.constants.TradeStatus;

import java.util.Date;

/**
 * Created by daoxing on 2016/9/23.
 */
public class OrderQueryResponse extends WxpayResponseBase {
    public static final String KEY_OPENID          = "openid";
    public static final String KEY_TRANSACTION_ID  = "trancaction_id";
    public static final String KEY_TRADE_STATE     = "trade_state";
    public static final String KEY_TOTAL_FEE       = "total_fee";
    public static final String KEY_CASH_FEE        = "cash_fee";
    public static final String KEY_COUPON_FEE      = "coupon_fee";
    public static final String KEY_COUPON_COUNT    = "coupon_count";
    public static final String KEY_TIME_END        = "time_end";
    public OrderQueryResponse(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }

    // PROPERTY
    // ID
    public String getTransactionId()
    {
        return(
                super.getProperty(KEY_TRANSACTION_ID)
        );
    }

    public String getOpenid()
    {
        return(
                super.getProperty(KEY_OPENID)
        );
    }

    // STATUE
    public TradeStatus getTradeState()
    {
        return(
                TradeStatus.valueOf(
                        super.getProperty(KEY_TRADE_STATE)
                )
        );
    }

    // TIME
    public Date getTimeEnd(){
        return(
                super.getDateProperty(KEY_TIME_END)
        );
    }

    // FEE
    public final Integer getTotalFee()
    {
        return(
                super.getIntProperty(KEY_TOTAL_FEE)
        );
    }

    public final Integer getTotalFeeFen()
    {
        return(
                this.getTotalFee()
        );
    }

    public final Double getTotalFeeYuan()
    {
        Integer fen = this.getTotalFee();
        return(
                fen!=null ? (fen.doubleValue()/100.0) : null
        );
    }

    public final Integer getCashFee()
    {
        return(
                super.getIntProperty(KEY_CASH_FEE)
        );
    }

    public final Integer getCashFeeFen()
    {
        return(
                this.getCashFee()
        );
    }

    public final Double getCashFeeYuan()
    {
        Integer fen = this.getCashFee();
        return(
                fen!=null ? (fen.doubleValue()/100.0) : null
        );
    }

    public final Integer getCouponFee()
    {
        return(
                super.getIntProperty(KEY_COUPON_FEE)
        );
    }

    public final Integer getCouponFeeFen()
    {
        return(
                this.getCouponFee()
        );
    }

    public final Double getCouponFeeYuan()
    {
        Integer fen = this.getCouponFee();
        return(
                fen!=null ? (fen.doubleValue()/100.0) : null
        );
    }

    // COUPON
    public final Integer getCouponCount()
    {
        return(
                this.getIntProperty(KEY_COUPON_COUNT)
        );
    }
}
