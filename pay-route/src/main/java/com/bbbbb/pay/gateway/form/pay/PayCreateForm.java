package com.bbbbb.pay.gateway.form.pay;

import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.pay.gateway.form.SignForm;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class PayCreateForm extends SignForm{


    /**
     * 商户订单号
     */
    @NotBlank
    private String orderNo;
    /**
     * 支付方式
     * WX:微信APP
     * WX_PUB:公众号
     * WX_QR:微信二维码
     * ..........
     */
    @NotBlank
    private String channel;
    /**
     * 支付金额
     */
    @NotNull
    private Integer amount;
    /**
     * 商品名称
     */
    @NotBlank
    private String subject;
    /**
     * 付款终端的IP地址
     */
    private String spbillCreateIp;
    /**
     * 支付帐号
     * 微信公众号支时,是openid
     * 银行支卡支付时是银行卡号
     *
     */
    private String account;
    /**
     * 不同支付渠道所需的其它参数
     */
    private Map<String, String> extra;
    /**
     * 支付成后，终端返回的URL
     * 支付宝应用外支付成功跳转地址
     */
    private String returnUrl;

    /**
     * 支付成功通知
     */
    private String callBackUrl;
}
