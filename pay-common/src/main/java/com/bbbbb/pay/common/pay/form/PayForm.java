package com.bbbbb.pay.common.pay.form;

import com.bbbbb.pay.common.constants.Channel;
import lombok.Data;

/**
 * 
 * 支付表单
 */
@Data
public class PayForm {

    /**
     * 外部订单号
     */
    private String orderNo;
    /**
     * 支付方式
     */
    private Channel channel;
    /**
     * 支付金额,单位分
     */
    private Integer amount;
    /**
     * 商品名称
     */
    private String subject;
    /**
     * 商口描述
     */
    private String body;
    /**
     * 支付帐号，可能是OpenId ,也可能是银行卡号，也可能是绑定编号
     */
    private String account;

    /**
     *发起请求的用户ip地址
     */
    private String ip;
    
    /**
     * 用户标识
     */
    private String identityId;
}
