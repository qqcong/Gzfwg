package com.bwoil.pay.channel.weixin.request;




import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.channel.weixin.response.RedpackQueryResponse;
import com.bwoil.pay.channel.weixin.util.RandomStringGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by daoxing on 2016/10/10.
 */
public class RedpackQuery extends WxpayRequestBase  {

    // KEYS
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";

    public static final String KEY_MCH_BILL_NO      = "mch_billno";//订单号
    public static final String KEY_BILL_TYPE         = "bill_type";
    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            "appid",
            "bill_type",
            "mch_billno",
            "mch_id",
            "nonce_str",
            "sign"


    );


    public RedpackQuery(WeixinAccount mpAct) {
        super(mpAct);
        conf=new Properties();
        this.mpAct=mpAct;
        /*
         *从商户户初始化部分参数
         */
        conf.put(KEY_APPID,mpAct.getAppId());
        conf.put(KEY_MCH_ID,mpAct.getMchId());
        conf.put(KEY_BILL_TYPE,"MCHT");
        conf.put(KEY_NONCE_STR, RandomStringGenerator.getRandomStringByLength(8));//生机随机数
    }

    public RedpackQuery setOrderNo(String orderNo){
        conf.put(KEY_MCH_BILL_NO,orderNo);
        return this;
    }
    @Override
    public RedpackQuery build() {
        return this;
    }

    @Override
    public RedpackQueryResponse execute() throws WechatApiException, WechatRunTimeException {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        String respXml = super.executePostXML(url, body,true);

        return(new RedpackQueryResponse(this.mpAct,respXml));
    }
}
