package com.bwoil.pay.channel.weixin.request;


import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.channel.weixin.response.WxpayResponseBase;
import com.bwoil.pay.channel.weixin.util.HttpTool;
import com.bwoil.pay.channel.weixin.util.RandomStringGenerator;
import com.bwoil.pay.channel.weixin.util.SSLSocketFactoryBuilder;
import com.bwoil.pay.channel.weixin.util.Signature;
import lombok.extern.apachecommons.CommonsLog;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 微信支付请请求基类
 * Created by daoxing on 2016/9/23.
 */
@CommonsLog
public abstract  class WxpayRequestBase {

    public static final String KEY_APPID         = "appid";//APPID
    public static final String KEY_SIGN          = "sign";//签名
    public static final String KEY_NOTIFY_URL    = "notify_url";//通知回调URL
    public static final String KEY_NONCE_STR     = "nonce_str";//随机码
    public static final String KEY_MCH_ID           = "mch_id";//商户编号


    /**
     * 请求参数
     */
    protected Properties conf;


    /**
     * 支付微信帐号
     */

    protected WeixinAccount mpAct;

    /**
     * 构造方法传入支付帐号
     * @param mpAct
     */
    public WxpayRequestBase(WeixinAccount mpAct)
    {
        conf=new Properties();
        this.mpAct=mpAct;
        /*
         *从商户户初始化部分参数
         */
        conf.put(KEY_APPID,mpAct.getAppId());
        conf.put(KEY_MCH_ID,mpAct.getMchId());
        conf.put(KEY_NONCE_STR, RandomStringGenerator.getRandomStringByLength(8));//生机随机数
    }

    /**
     * 生成请求XML
     * @param paramNames  请求参数列表
     * @return
     */
    protected String buildXMLBody(List<String> paramNames)
    {
        StringBuilder xml = new StringBuilder();

        xml.append("<xml>");

        for (String k:paramNames)
        {
            Object v = conf.get(k);//this.getProperty(k);
            if (v != null)
                xml.append('<').append(k).append('>')
                        .append(v)
                        .append("</").append(k).append('>');
        }

        xml.append("</xml>");
        System.out.println(xml.toString());
        return(xml.toString());
    }

    /**
     * 构建对象
     * @return
     */
    public abstract WxpayRequestBase build();


    /**
     * 获取参数值
     * @param key
     * @return
     */
    public String getProperty(String key)
    {
        return  this.conf.getProperty(key);
    }

    /**
     * 设置参数值
     * @param key
     * @param value
     * @return
     */
    public WxpayRequestBase setProperty(String key, String value)
    {
        if(value!=null&&key!=null) {
            this.conf.setProperty(key, value);
        }else{

        }
        return(this);
    }

    /**
     * 批量设置参数
     * @param aConf
     * @return
     */
    public WxpayRequestBase setProperties(Map<String,String> aConf)
    {
        this.conf.putAll(aConf);
        return(this);
    }

    /**
     * 签名
     * @return
     */

    public WxpayRequestBase sign()

    {

        if (mpAct.getPayKey() == null)
            throw(new IllegalArgumentException("KEY required to sign, but not found."));

        String sign=  Signature.getSign(this.conf,mpAct.getPayKey());

        this.conf.setProperty(KEY_SIGN, sign);
        return(this);
    }


    /**
     * 提交请求
     * @param fullURL  请求URL
     * @param bodyXML   请求XML内容
     * @param isUseSSL  是否需要使用支付证书
     * @return 返回微信服务器返回的XML内容
     */
    public String executePostXML(String fullURL, String bodyXML,boolean isUseSSL){
        try {
            String s=null;
            log.info("提交微信支付XML:\n"+bodyXML);
            if(isUseSSL){
                s = HttpTool.post4SSL(fullURL, bodyXML, SSLSocketFactoryBuilder.getSSLSocketFactory(mpAct));
            }else {
                s = HttpTool.post(fullURL, bodyXML);
            }
            log.info("提交微信支付API返回:\n"+s);
            return s;
        }catch (Exception e){
            throw new RuntimeException("读取证书失败",e);
        }


    }

    /**
     * 执行请求
     * @return  将返回的内容转换成WxpayResponseBase
     * @throws WechatApiException
     * @throws WechatRunTimeException
     */
    public abstract WxpayResponseBase execute() throws WechatApiException, WechatRunTimeException;

}
