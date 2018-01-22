package com.bbbbb.pay.channel.weixin.response;


import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;

/**
 * 订单关闭结果
 * Created by daoxing on 2016/9/24.
 */
public class OrderCloseResponse extends WxpayResponseBase  {

    public OrderCloseResponse(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }
}
