package com.bwoil.pay.common.pay.form;

import com.bwoil.common.framework.validation.Chinese;
import com.bwoil.common.framework.validation.Mobile;
import com.bwoil.common.framework.validation.NotBlank;
import com.bwoil.common.framework.validation.URL;

import lombok.Data;


/**
 * @author chendx
 *首次支付（绑卡+支付）
 */
@Data
public class FirstPayForm {
	
	private BindSubmitForm cardInfo;

    private PayForm   payInfo;

	private String appid;
    /**
     * 银行简码
     */
    private String bankCode;
    @NotBlank
    private String userId;//	是	string	用户id
    @NotBlank
    private String orderNo;//	是	string	支付外部订单号
    @NotBlank
    private String requestNo;
    @NotBlank
    private String amount;//	是	string	支付金额
    @NotBlank
    private String productName;//	是	string	商品名称
    private String ip;//	否	string	支付终端的IP地址
    @NotBlank
    private String cardNo;//	是	string	卡号
    @NotBlank
    @URL
    private String callbackUrl;//	是	string	支付回调地址
    @NotBlank
    private String idCardNo;//	是	string	身份证号
    @NotBlank
    @Chinese
    private String userName	;//是	string	用户姓名
    private String channel;//	否	string	支付渠道 默认：”ALL” 全部 可选 “YEEPAY”，”NEWPAY”
    @NotBlank
    @Mobile
    private String mobile;//	是	string	手机号
}
