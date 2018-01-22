package com.bbbbb.pay.channel.weixin.response;



import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;

import java.util.Date;

/**
 * Created by daoxing on 2016/10/10.
 */
public class TransfersResponse extends WxpayResponseBase {
    public static final String KEY_TRADE_NO       = "partner_trade_no";
    public static final String KEY_PAYMENT_NO          = "payment_no";
    public static final String KEY_PAYMENT_TIME      = "payment_time";
    public TransfersResponse(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }

    public  String getTradeNo() {
        return super.getProperty(KEY_TRADE_NO);

    }

    public  String getPaymentNo() {
        return super.getProperty(KEY_PAYMENT_NO);

    }

    public Date getPaymentTime() {
        return super.getDateProperty(KEY_PAYMENT_TIME,"yyyy-MM-dd HH:mm:ss");
    }
}
