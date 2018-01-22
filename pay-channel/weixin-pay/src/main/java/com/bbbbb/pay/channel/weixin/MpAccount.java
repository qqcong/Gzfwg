package com.bbbbb.pay.channel.weixin;

import lombok.Data;


/**
 * Created by Administrator on 2017/7/4.
 */
@Data
public class MpAccount implements WeixinAccount {
    /**
     * 应用Id
     */

    private String appId;

    //以下是微支付需求参数
    //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）

    private String mchId;

    //HTTPS证书的字符串
    private  String cert ;
    private String payKey;

    private String notifyUrl;

    private String wapUrl;
    private String wapName;
}
