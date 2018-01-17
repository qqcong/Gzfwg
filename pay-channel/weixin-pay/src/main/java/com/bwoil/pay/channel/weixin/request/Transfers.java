package com.bwoil.pay.channel.weixin.request;




import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.channel.weixin.response.TransfersResponse;
import com.bwoil.pay.channel.weixin.util.RandomStringGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 微信企业转帐
 * Created by daoxing on 2016/10/10.
 */
public class Transfers  extends WxpayRequestBase{

    // KEYS
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public static final String KEY_MCH_APP_ID        = "mch_appid";
    public static final String KEY_MCH_ID            = "mchid";
    public static final String KEY_TRADE_NO          = "partner_trade_no";
    public static final String KEY_OPEN_ID       = "openid";
    public static final String KEY_CHECK_NAME       = "check_name";
    public static final String KEY_RE_USER_NAME         = "re_user_name";

    public static final String KEY_AMOUNT         = "amount";
    public static final String KEY_DESC        = "desc";
    public static final String KEY_SPBILL_CREAT_IP         = "spbill_create_ip";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            "mch_appid",
            "mchid",
            "device_info",
            "partner_trade_no",
            "nonce_str",
            "openid",
            "check_name",
            "re_user_name",
            "amount",
            "desc",
            "spbill_create_ip",
            "sign"


    );
    public Transfers(WeixinAccount mpAct) {
        super(mpAct);
        conf=new Properties();
        this.mpAct=mpAct;
        /*
         *从商户户初始化部分参数
         */
        conf.put(KEY_MCH_APP_ID,mpAct.getAppId());
        conf.put(KEY_MCH_ID,mpAct.getMchId());
        conf.put(KEY_NONCE_STR, RandomStringGenerator.getRandomStringByLength(8));//生机随机数
    }

    @Override
    public Transfers build() {
        return  this;
    }

    @Override
    public TransfersResponse execute() throws WechatApiException, WechatRunTimeException {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        String respXml = super.executePostXML(url, body,true);

        return(new TransfersResponse(this.mpAct,respXml));
    }


    public Transfers  setTradeNo(String tradeNo) {
        conf.put(KEY_TRADE_NO,tradeNo);
        return this;
    }
    public Transfers  setOpenid(String opneid) {
        conf.put(KEY_OPEN_ID,opneid);
        return this;
    }
    public Transfers  setCheckName(boolean checkName) {
        if(checkName){
            conf.put(KEY_CHECK_NAME,"FORCE_CHECK");
        }else{
            conf.put(KEY_CHECK_NAME,"NO_CHECK");
        }

        return this;
    }
    public Transfers  setReUserName(String reUserName) {
        conf.put(KEY_RE_USER_NAME,reUserName);
        return this;
    }
    public Transfers  setAmount(Integer amount) {
        conf.put(KEY_AMOUNT,amount);
        return this;
    }
    public Transfers  setDesc(String desc) {
        conf.put(KEY_DESC,desc);
        return this;
    }
    public Transfers  setSpbillCreatIp(String spbillCreatIp) {
        conf.put(KEY_SPBILL_CREAT_IP,spbillCreatIp);
        return this;
    }
}
