package com.bwoil.pay.common.pay;

import com.bwoil.pay.common.pay.result.PayResult;


/**
 * @author chendx
 * 支付结果通知
 */
public interface PayNotity {
    void notify(PayResult result);
}
