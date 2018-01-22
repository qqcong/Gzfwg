package com.bbbbb.pay.channel.weixin.response;



import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;


import java.util.Date;

/**
 * Created by daoxing on 2016/10/24.
 */
public class TransfersQueryResponse extends WxpayResponseBase {
    public static final String KEY_TRADE_NO       = "partner_trade_no";
    public static final String KEY_MCH_ID          = "mch_id";
    public static final String KEY_DETAIL_ID      = "detail_id";
    public static final String KEY_STATUS       = "status";
    public static final String KEY_REASON          = "reason";
    public static final String KEY_OPENID      = "openid";
    public static final String KEY_TRANSFER_NAME       = "transfer_name";
    public static final String KEY_PAYMENT_AMOUNT          = "payment_amount";
    public static final String KEY_TRANSFER_TIME      = "transfer_time";
    public static final String KEY_DESC      = "desc";
    public TransfersQueryResponse(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }


    public  String getTradeNo() {
        return super.getProperty(KEY_TRADE_NO);

    }

    public  String getMchId() {
        return super.getProperty(KEY_MCH_ID);

    }

    public  String getDetailId() {
        return super.getProperty(KEY_DETAIL_ID);


    }

    public  String getStatus() {
        return super.getProperty(KEY_STATUS);
    }

    public  String getReason() {
        return super.getProperty(KEY_REASON);
    }

    public  String getOpenid() {
        return super.getProperty(KEY_OPENID);
    }

    public  String getTransferName() {
        return super.getProperty(KEY_TRANSFER_NAME);

    }

    public  Integer getPaymentAmount() {
        return super.getIntProperty(KEY_PAYMENT_AMOUNT);

    }

    public Date getTransferTime() {
        return super.getDateProperty(KEY_TRANSFER_TIME,"yyyy-MM-dd HH:mm:ss");

    }

    public  String getDesc() {
        return super.getProperty(KEY_DESC);

    }
}
