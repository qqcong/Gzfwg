package com.bbbbb.pay.channel.weixin;

/**
 * 微信公众号信息
 * 
 * 
 * @since 2.0
 */

public interface WeixinAccount {



    String getAppId();


    String getMchId();

    String getCert();

    String getPayKey() ;

    String getNotifyUrl() ;
}
