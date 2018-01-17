package com.bwoil.pay.channel.weixin.request;





import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.channel.weixin.response.SendRedpackResponse;
import com.bwoil.pay.channel.weixin.util.RandomStringGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 微信红包
 * Created by daoxing on 2016/10/9.
 */
public class SendRedpack extends WxpayRequestBase {

    // KEYS
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";

    public static final String KEY_MCH_BILL_NO              = "mch_billno";//订单号
    public static final String KEY_WX_APPID      = "wxappid";
    public static final String KEY_SEND_NAME         = "send_name";
    public static final String KEY_RE_OPENID  = "re_openid";
    public static final String KEY_TOTAL_AMOUT        = "total_amount";
    public static final String KEY_TOTAL_NUM           = "total_num";
    public static final String KEY_WISHING        = "wishing";
    public static final String KEY_CLIENT_IP        = "client_ip";
    public static final String KEY_ACT_NAME       = "act_name";

    public static final String KEY_SEENE_ID       = "scene_id";
    public static final String KEY_RISK_INFO      = "risk_info";
    public static final String KEY_REMARK      = "remark";
    public static final String KEY_CONSUME_MCH_ID       = "consume_mch_id";

    public static final String KEY_MCT_ID       = "mch_id";
    public static final String KEY_NONCE_STR       = "nonce_str";
    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            "act_name",
            "client_ip",
            "consume_mch_id",
            "mch_billno",
            "mch_id",
            "nonce_str",
            "re_openid",
            "remark",
            "risk_info",
            "scene_id",
            "send_name",
            "sign",
            "total_amount",
            "total_num",
            "wishing",
            "wxappid"

    );

    public SendRedpack(WeixinAccount mpAct) {
        super(mpAct);
        conf.clear();
        conf=new Properties();
        this.mpAct=mpAct;
        /*
         *从商户户初始化部分参数
         */
        conf.put(KEY_WX_APPID,mpAct.getAppId());
        conf.put(KEY_MCT_ID,mpAct.getMchId());
        conf.put(KEY_NONCE_STR, RandomStringGenerator.getRandomStringByLength(8));//生机随机数
    }
   public SendRedpack setMchBillno(String mchBillno){
       conf.put(KEY_MCH_BILL_NO,mchBillno);
       return this;
   }
    public SendRedpack setSendName(String sendName){
        conf.put(KEY_SEND_NAME,sendName);
        return this;
    }
    public SendRedpack setOpenid(String openid){
        conf.put(KEY_RE_OPENID,openid);
        return this;
    }
    public SendRedpack setTotalAmout(Integer toatlAmout){
        conf.put(KEY_TOTAL_AMOUT,toatlAmout+"");
        return this;
    }
    public SendRedpack setTotalNum(Integer totalNum){
        conf.put(KEY_TOTAL_NUM,totalNum);
        return this;
    }
    public SendRedpack setWishing(String wishing){
        conf.put(KEY_WISHING,wishing);
        return this;
    }
    public SendRedpack setClientIp(String clientIp){
        conf.put(KEY_CLIENT_IP,clientIp);
        return this;
    }
    public SendRedpack setActName(String actName){
        conf.put(KEY_ACT_NAME,actName);
        return this;
    }
    public SendRedpack setSeeneId(String seeneId){
        conf.put(KEY_SEENE_ID,seeneId);
        return this;
    }
    public SendRedpack setRiskInfo(String riskInfo){
        conf.put(KEY_RISK_INFO,riskInfo);
        return this;
    }
    public SendRedpack setRemark(String remark){
        conf.put(KEY_REMARK,remark);
        return this;
    }
    public SendRedpack setConsumeMchId(String consumeMchId){
        conf.put(KEY_CONSUME_MCH_ID,consumeMchId);
        return this;
    }
    @Override
    public SendRedpack build() {
        return this;
    }

    @Override
    public SendRedpackResponse execute() throws WechatApiException, WechatRunTimeException {

        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        String respXml = super.executePostXML(url, body,true);

        return(new SendRedpackResponse(this.mpAct,respXml));

    }





}
