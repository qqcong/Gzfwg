package com.bwoil.pay.common.pay.result;

import java.util.Date;
import java.util.Map;

import com.bwoil.pay.common.constants.TradeStatus;

import lombok.Data;


/**
 *@author chendx
 * 支付结果
 */
@Data
public class PayResult {

    private String orderNo;
    /**
     * 是否已支付
     */
    private boolean paid=false;

    /**
     *实际支付金额,单位分
     */
    private Integer payAmount;


    /**
     *支付完成时间
     */
    private Date timePaid;


    /**
     *第三方支付订单编号
     */
    private String transactionNo;

    /**
     * 错误编码
     */
    private String errCode;

    /**
     * 错误消息
     */
    private String errMsg;


    /**
     *交易状态
     */
    private TradeStatus tradeStatus;


    /**
     * 支付渠道元数据
     */
    private Map<String,String> metaData;

    private String bindId;

    private String status;
    
    private String amount; 
    
    private String msg;
    



    /**
     * 支付凭证，用于调起支付控件
     */
    private Map<String,String> credential;
}
