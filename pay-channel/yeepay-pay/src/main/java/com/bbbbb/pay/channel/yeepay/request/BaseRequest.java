package com.bbbbb.pay.channel.yeepay.request;


import com.bbbbb.common.framework.util.DateUtils;
import com.bbbbb.common.framework.util.HttpUtils;
import com.bbbbb.common.framework.util.JsonUtils;


import com.bbbbb.pay.channel.yeepay.response.BaseResponse;
import com.bbbbb.pay.channel.yeepay.util.AES;
import com.bbbbb.pay.channel.yeepay.util.EncryUtil;
import com.bbbbb.pay.channel.yeepay.util.RSA;
import com.bbbbb.pay.channel.yeepay.util.RandomUtil;

import com.bbbbb.pay.common.pay.PayException;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * 操作基础类
 */
@CommonsLog
public abstract class BaseRequest {
    //商户号
    public static final String KEY_APPID         = "merchantno";
    //签名
    public static final String KEY_SIGN          = "sign";
    protected TreeMap<String,Object> dataMap;
    private static final String CHARSET 			= "UTF-8";
    protected Map<String,String> config;

    private String merchantPrivateKey;

    private String yeepayPublicKey;


    private String merchantNo;
    /**
     * 初始化
     * @param config
     */
   public BaseRequest(Map<String,String> config){

       dataMap=new TreeMap<>();
       /*
        *从商户户初始化部分参数
        */
       dataMap.put(KEY_APPID,config.get("merchantno"));

       merchantPrivateKey =config.get("merchantPrivateKey");
       yeepayPublicKey =config.get("yeepayPublicKey");
       this.merchantNo=config.get(KEY_APPID);

   }

   public BaseRequest setIdentityId(String identityId){
       dataMap.put("identityid",identityId);
       dataMap.put("identitytype","PHONE");
       return this;
   }

   protected   BaseRequest sign(){

       String sign= EncryUtil.handleRSA(dataMap, merchantPrivateKey);
       dataMap.put(KEY_SIGN,sign);
       return this;

   }


    /**
     * 设置参数值
     * @param key
     * @param value
     * @return
     */
    public BaseRequest setProperty(String key, Object value)
    {
        if(value!=null&&key!=null) {
            this.dataMap.put(key, value);
        }
        return(this);
    }

    /**
     * 批量设置参数
     * @param map
     * @return
     */
    public BaseRequest setProperties(Map<String,Object> map)
    {
        this.dataMap.putAll(map);
        return(this);
    }
    /**
     * 提交请求
     * @param fullURL  请求URL
     * @return 提交的参数据
     */
    public String execute(String fullURL){
        try {
            log.info("提交易宝支付JSON:\n"+JsonUtils.toString(dataMap));
            String sign					= EncryUtil.handleRSA(dataMap, merchantPrivateKey);
            dataMap.put("sign", sign);
            String merchantAESKey=buildAESKey();
            String encryptkey	= RSA.encrypt(merchantAESKey, yeepayPublicKey);
            String jsonStr		= JsonUtils.toString(dataMap);
            String data				= AES.encryptToBase64(jsonStr, merchantAESKey);



            //请求参数为如下三者：merchantno、data、enrcyptkey
            Map<String, String> paramMap	= new HashMap<>(3);
            paramMap.put(KEY_APPID, merchantNo);
            paramMap.put("data", data);
            paramMap.put("encryptkey", encryptkey);

            String responseBody	= HttpUtils.sendHttpRequest(fullURL, paramMap, CHARSET, true);
            log.info("易宝支付API返回:\n"+responseBody);

            //将String转化为Map
            Map<String, String> jsonMap=JsonUtils.toTreeMap(responseBody);


            String dataFromYeepay		= formatString(jsonMap.get("data"));
            String encryptkeyFromYeepay	= formatString(jsonMap.get("encryptkey"));
            //验证签名
            boolean signMatch = EncryUtil.checkDecryptAndSign(dataFromYeepay, encryptkeyFromYeepay,
                    yeepayPublicKey, merchantPrivateKey);
            //签名不正确
            if(!signMatch) {
                log.error("签名错误");
                throw new RuntimeException("签名错误");
            }

            String yeepayAESKey		= RSA.decrypt(encryptkeyFromYeepay, merchantPrivateKey);
            String decryptData		= AES.decryptFromBase64(dataFromYeepay, yeepayAESKey);
            log.info("易宝解密后数据:"+decryptData);


            return decryptData;
        }catch (Exception e){
            log.error("提交易宝支付异常",e);
            throw new RuntimeException("提交易宝支付异常",e);
        }


    }
    public abstract BaseResponse execute() throws PayException;
    /**
     * 生成AESKey: 16位的随机串
     */
    private static String buildAESKey() {
        return RandomUtil.getRandom(16);
    }

    /**
     * 格式化字符串
     */
    public static String formatString(String text) {
        return (text == null ? "" : text.trim());
    }
}
