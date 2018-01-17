package com.bwoil.pay.channel.weixin.response;


import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.common.constants.RefundStatus;

/**
 * Created by daoxing on 2016/9/23.
 */
public class RefundQueryResponse extends WxpayResponseBase {
    public static final String KEY_REFUND_COUNT             = "refund_count";
    public static final String KEY_REFUND_ID_$0             = "refund_id_$0";
    public static final String KEY_OUT_REFUND_NO_$0         = "out_refund_no_$0";
    public static final String KEY_REFUND_FEE_$0            = "refund_fee_$0";
    public static final String KEY_REFUND_STATUS_$0         = "refund_status_$0";
    public static final String KEY_COUPON_REFUND_COUNT_$0   = "coupon_refund_count_$0";
    public static final String KEY_COUPON_REFUND_FEE_$0     = "coupon_refund_fee_$0";
    public RefundQueryResponse(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }

    // PROPERTY
    // REFUND_COUND
    public final int getRefundCount()
    {
        return Integer.valueOf(
                super.getProperty(KEY_REFUND_COUNT)
        );
    }

    // REFUND_ID
    public String getRefundId(int n)
    {
        return(
                super.getProperty(
                        materializeParamName(KEY_REFUND_ID_$0, n)
                )
        );
    }

    public String[] getRefundId()
    {
        int count = this.getRefundCount();
        String[] v = new String[count];

        for (int i=0; i<count; i++)
            v[i] = this.getRefundId(i);

        return(v);
    }

    // OUT_REFUND_NO
    public String getOutRefundNo(int n)
    {
        return(
                super.getProperty(
                        materializeParamName(KEY_OUT_REFUND_NO_$0, n)
                )
        );
    }

    public String[] getOutRefundNo()
    {
        int count = this.getRefundCount();
        String[] v = new String[count];

        for (int i=0; i<count; i++)
            v[i] = this.getOutRefundNo(i);

        return(v);
    }

    // REFUND_STATUS
    public RefundStatus getRefundStatus(int n)
    {
        return(
                RefundStatus.valueOf(
                        super.getProperty(
                                materializeParamName(KEY_REFUND_STATUS_$0, n)
                        )
                )
        );
    }

    public RefundStatus[] getRefundStatus()
    {
        int count = this.getRefundCount();
        RefundStatus[] v = new RefundStatus[count];

        for (int i=0; i<count; i++)
            v[i] = this.getRefundStatus(i);

        return(v);
    }

    // REFUND_FEE
    /** @return in CNY fen
     */
    public Integer getRefundFee(int n)
    {
        return(
                super.getIntProperty(
                        materializeParamName(KEY_REFUND_FEE_$0, n)
                )
        );
    }

    public Integer[] getRefundFee()
    {
        int count = this.getRefundCount();
        Integer[] v = new Integer[count];

        for (int i=0; i<count; i++)
            v[i] = this.getRefundFee(i);

        return(v);
    }

    // COUPON
    public final Integer getCouponRefundFee(int n)
    {
        return(
                super.getIntProperty(
                        materializeParamName(KEY_COUPON_REFUND_FEE_$0, n)
                )
        );
    }

    public Integer[] getCouponRefundFee()
    {
        int count = this.getRefundCount();
        Integer[] v = new Integer[count];

        for (int i=0; i<count; i++)
            v[i] = this.getCouponRefundFee(i);

        return(v);
    }

    public Integer getCouponRefundCount(int n)
    {
        return(
                super.getIntProperty(
                        materializeParamName(KEY_COUPON_REFUND_COUNT_$0, n)
                )
        );
    }

    public Integer[] getCouponRefundCount()
    {
        int count = this.getRefundCount();
        Integer[] v = new Integer[count];

        for (int i=0; i<count; i++)
            v[i] = this.getCouponRefundCount(i);

        return(v);
    }
}
