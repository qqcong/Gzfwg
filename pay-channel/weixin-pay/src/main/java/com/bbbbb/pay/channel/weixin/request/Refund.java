package com.bbbbb.pay.channel.weixin.request;



import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;
import com.bbbbb.pay.channel.weixin.response.RefundResponse;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 退款请求
 * Created by daoxing on 2016/9/23.
 */
public class Refund extends WxpayRequestBase {
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public static final String KEY_OUT_TRADE_NO      = "out_trade_no";
    public static final String KEY_OUT_REFUND_NO     = "out_refund_no";
    public static final String KEY_TRANSACTION_ID    = "transaction_id";
    public static final String KEY_TOTAL_FEE         = "total_fee";
    public static final String KEY_REFUND_FEE        = "refund_fee";
    public static final String KEY_OP_USER_ID        = "op_user_id";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            "appid",
            "device_info",
            "mch_id",
            "nonce_str",
            "op_user_id",
            "out_refund_no",
            "out_trade_no",
            "refund_fee",
            "refund_fee_type",
            "sign",
            "total_fee",
            "transaction_id"
    );

    public Refund(WeixinAccount mpAct) {
        super(mpAct);
    }

    // BUILD
    @Override
    public Refund build()
    {
        return (this);
    }




    // EXECUTE
    @Override
    public RefundResponse execute()
            throws WechatRunTimeException, WechatApiException
    {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        String respXml = super.executePostXML(url, body,true);

        return(new RefundResponse(mpAct,respXml));
    }

    // PROPERTY
    /** 商户系统内部的订单号,32个字符内、可包含字母
     */
    public Refund setOutTradeNo(String outTradeNo)
    {
        super.setProperty(KEY_OUT_TRADE_NO, outTradeNo);

        return(this);
    }

    /** 微信的订单号，优先使用
     */
    public Refund setTransactionId(String transactionId)
    {
        super.setProperty(KEY_TRANSACTION_ID,transactionId);

        return (this);
    }

    /** 商户系统内部的退单号,32个字符内、可包含字母
     */
    public Refund setOutRefundNo(String outRefundNo)
    {
        super.setProperty(KEY_OUT_REFUND_NO,outRefundNo);

        return (this);
    }

    /** 订单总金额，单位为分，不能带小数点
     */
    public Refund setTotalFee(int totalFeeInCNYFen)
    {
        super.setProperty(KEY_TOTAL_FEE, Integer.toString(totalFeeInCNYFen));

        return(this);
    }

    /** wrap method
     */
    public Refund setTotalFee(BigDecimal totalFeeInCNYYuan)
    {
        this.setTotalFee(
                totalFeeInCNYYuan.multiply(new BigDecimal(100) ).intValue()
        );

        return(this);
    }

    /** 退款总金额,单位为分,可以做部分退款
     */
    public Refund setRefundFee(int refundFeeInCNYFen)
    {
        super.setProperty(KEY_REFUND_FEE,Integer.toString(refundFeeInCNYFen));

        return (this);
    }

    /** wrap method
     */
    public Refund setRefundFee(double totalFeeInCNYYuan)
    {
        this.setRefundFee(
                Double.valueOf(totalFeeInCNYYuan * 100.0).intValue()
        );

        return(this);
    }

    /** 操作员帐号, 默认为商户号
     */
    public Refund setOpUserId(String opUserId)
    {
        super.setProperty(KEY_OP_USER_ID, opUserId);

        return (this);
    }

}
