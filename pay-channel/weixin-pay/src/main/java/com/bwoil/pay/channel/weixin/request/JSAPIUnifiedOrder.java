package com.bwoil.pay.channel.weixin.request;


import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.constants.TradeType;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;

/**
 * 微信公众号支付
 * Created by daoxing on 2016/9/23.
 */
public class JSAPIUnifiedOrder extends UnifiedOrder{

    // COSTRUCT
    public JSAPIUnifiedOrder(WeixinAccount mpAct)throws WechatApiException, WechatRunTimeException
    {
        super(mpAct);

        super.setTradeType(TradeType.JSAPI);

        return;
    }


}
