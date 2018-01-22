package com.bbbbb.pay.channel.weixin.response;


import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;

/**
 * Created by daoxing on 2016/9/23.
 */
public class UnifiedOrderResponse extends WxpayResponseBase{

    public UnifiedOrderResponse(WeixinAccount mpAct, String xml)throws WechatApiException, WechatRunTimeException

    {
        super(mpAct,xml);

        return;
    }
}
