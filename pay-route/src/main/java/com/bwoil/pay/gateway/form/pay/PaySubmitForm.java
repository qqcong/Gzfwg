package com.bwoil.pay.gateway.form.pay;

import com.bwoil.common.framework.validation.BankCard;
import com.bwoil.common.framework.validation.Money;
import com.bwoil.common.framework.validation.NotBlank;
import com.bwoil.common.framework.validation.URL;
import com.bwoil.pay.gateway.form.SignForm;
import lombok.Data;

@Data
public class PaySubmitForm extends SignForm{

    /**
     * 请求单号
     */
    @NotBlank
    private String requestNo;
    @NotBlank
    private String userId;;//	是	string	用户id
    @NotBlank
    private String orderNo;//	是	string	支付外部订单号
    @NotBlank
    @Money
    private String amount;//	是	string	支付金额
    @NotBlank
    private String productName;//	是	string	商品名称
    private String channel;//	否	string	支付渠道，默认快捷支付
    private String ip;//	否	string	支付终端的IP地址
    @BankCard
    private String cardNo;//	否	string	卡号
    private String bindId;//	否	string

    /**
     * 支付回调地址
     */
    @URL
    @NotBlank
    private String callbackUrl;
}
