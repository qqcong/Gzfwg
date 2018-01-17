package com.bwoil.pay.common.pay;

import com.bwoil.pay.common.pay.form.FirstPayForm;
import com.bwoil.pay.common.pay.result.PayResult;

/**
 * @author chendx
 * 快捷支付接口
 */
public interface FastPay extends Pay {

    /**
     * 确认支付
     * 仅适用于快捷支付
     * @param smsCode  验验码
     * @param transactionNo 支付流水号
     * @return 支付结果
     */
    PayResult comfirm(String transactionNo, String smsCode);


    /**
     * 首次支付
     * 仅适用于快捷支付  包含绑卡和支付
     * @return 支付结果
     */
    PayResult firstPay(FirstPayForm form);
    
    /**
     * YEEPAY支持，宝付不支持
     * @return
     */
    boolean isSupportFirstPay() ;
}
