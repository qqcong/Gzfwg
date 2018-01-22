package com.bbbbb.pay.channel.weixin.request;


import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.constants.TradeType;

/**
 * 微信公众号二维码支付
 * Created by daoxing on 2016/9/24.
 */
public class NativeUnifiedOrder extends UnifiedOrder {

    public NativeUnifiedOrder(WeixinAccount mpAct) {
        super(mpAct);
        super.setTradeType(TradeType.NATIVE);
    }

}
