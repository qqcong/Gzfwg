package com.bbbbb.pay.channel.weixin.request;





import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;
import com.bbbbb.pay.channel.weixin.response.OrderCloseResponse;
import com.bbbbb.pay.channel.weixin.response.WxpayResponseBase;

import java.util.Arrays;
import java.util.List;

/**
 * 关闭支付订单
 * Created by daoxing on 2016/9/24.
 */
public class CloseOrder extends WxpayRequestBase {
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/pay/closeorder";
    public static final String KEY_OUT_TRADE_NO      = "out_trade_no";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            "appid",
            "mch_id",
            "nonce_str",
            "out_trade_no",
            "sign"
    );
    public CloseOrder(WeixinAccount mpAct) {
        super(mpAct);
    }

    @Override
    public WxpayRequestBase build() {
        return this;
    }

    @Override
    public WxpayResponseBase execute() throws WechatApiException, WechatRunTimeException {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        String respXml = super.executePostXML(url, body,true);

        return(new OrderCloseResponse(mpAct,respXml));
    }
    public CloseOrder setOutTradeNo(String outTradeNo){
        this.setProperty(KEY_OUT_TRADE_NO,outTradeNo);
        return this;
    }
}
