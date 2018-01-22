package com.bbbbb.pay.channel.weixin.request;


import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;
import com.bbbbb.pay.channel.weixin.response.TransfersQueryResponse;
import com.bbbbb.pay.channel.weixin.util.RandomStringGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by daoxing on 2016/10/24.
 */
public class TransfersQuery  extends WxpayRequestBase{

    // KEYS
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";

    public static final String KEY_APP_ID        = "appid";
    public static final String KEY_MCH_ID            = "mch_id";
    public static final String KEY_TRADE_NO          = "partner_trade_no";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            "appid",
            "mch_id",
            "nonce_str",
            "partner_trade_no",
            "sign"


    );

    public TransfersQuery(WeixinAccount mpAct) {
        super(mpAct);
        conf=new Properties();
        this.mpAct=mpAct;
        /*
         *从商户户初始化部分参数
         */
        conf.put(KEY_APP_ID,mpAct.getAppId());
        conf.put(KEY_MCH_ID,mpAct.getMchId());
        conf.put(KEY_NONCE_STR, RandomStringGenerator.getRandomStringByLength(8));//生机随机数
    }
    public TransfersQuery setTradeNo(String tradeNo){
        conf.setProperty(KEY_TRADE_NO,tradeNo);
        return this;
    }
    @Override
    public TransfersQuery build() {
        return  this;
    }
    @Override
    public TransfersQueryResponse execute() throws WechatApiException, WechatRunTimeException {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        String respXml = super.executePostXML(url, body,true);

        return(new TransfersQueryResponse(this.mpAct,respXml));
    }


}
