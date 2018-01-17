package com.bwoil.pay.channel.weixin.response;


import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;

/**
 * 发送红包返回
 * Created by daoxing on 2016/10/9.
 */
public class SendRedpackResponse extends WxpayResponseBase {
    public static final String KEY_MCH_BILL_NO       = "mch_billno";
    public static final String KEY_MCH_ID           = "mch_id";
    public static final String KEY_WX_APPID      = "wxappid";
    public static final String KEY_RE_OPENID   = "re_openid";
    public static final String KEY_TOATL_AMOUNT  = "total_amount";
    public static final String KEY_SEND_LIST_ID  = "send_listid";
    public SendRedpackResponse(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }


    public  String getMchBillNo() {
        return super.getProperty(KEY_MCH_BILL_NO);
    }

    public  String getMchId() {
          return super.getProperty(KEY_MCH_ID);
    }

    public  String getAppid() {
        return  super.getProperty(KEY_WX_APPID);
    }

    public  String getOpenid() {
        return  super.getProperty(KEY_RE_OPENID);
    }

    public  Integer getToatlAmount() {
        return  super.getIntProperty(KEY_TOATL_AMOUNT);
    }

    public  String getSendListId() {
        return  super.getProperty(KEY_SEND_LIST_ID);

    }
}
