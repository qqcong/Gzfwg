package com.bwoil.pay.channel.weixin.request;


import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.constants.TradeType;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;

/**
 * 统一订单接品
 * Created by daoxing on 2016/9/23.
 */
public class APPUnifiedOrder extends UnifiedOrder{

    // COSTRUCT
    public APPUnifiedOrder(WeixinAccount mpAct)throws WechatApiException, WechatRunTimeException
    {
        super(mpAct);

        super.setTradeType(TradeType.APP);

        return;
    }
}
