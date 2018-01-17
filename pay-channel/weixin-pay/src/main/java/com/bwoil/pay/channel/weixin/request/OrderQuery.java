package com.bwoil.pay.channel.weixin.request;




import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.channel.weixin.response.OrderQueryResponse;

import java.util.Arrays;
import java.util.List;

/**
 * 订单查询请求
 * Created by daoxing on 2016/9/23.
 */
public class OrderQuery extends WxpayRequestBase{

    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/pay/orderquery";

    public static final String KEY_OUT_TRADE_NO      = "out_trade_no"; //商家订单号
    public static final String KEY_TRANSACTION_ID    = "transaction_id";//支付流水号

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            "appid",
            "mch_id",
            "nonce_str",
            "out_trade_no",
            "transaction_id",
            "sign"
    );
    //CONSTRUCT
    public OrderQuery(WeixinAccount mpAct){
        super(mpAct);

        return;
    }


    //BUILD
    @Override
    public OrderQuery build()
    {
        return(this);
    }





    // EXECUTE
    @Override
    public OrderQueryResponse execute() throws WechatRunTimeException, WechatApiException {
            String body = super.buildXMLBody(KEYS_PARAM_NAME);

            String respXml = super.executePostXML(URL_API_BASE, body, false);

            return new OrderQueryResponse(this.mpAct, respXml);
    }

    /** 商户系统内部的订单号,32个字符内、可包含字母
     */
    public OrderQuery setOutTradeNo(String outTradeNo)
    {
        super.setProperty(KEY_OUT_TRADE_NO, outTradeNo);

        return(this);
    }

    /** 微信的订单号，优先使用
     */
    public OrderQuery setTransactionId(String transactionId)
    {
        super.setProperty(KEY_TRANSACTION_ID,transactionId);

        return (this);
    }
}
