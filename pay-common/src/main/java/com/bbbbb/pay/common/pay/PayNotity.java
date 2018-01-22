package com.bbbbb.pay.common.pay;

import com.bbbbb.pay.common.pay.result.PayResult;


/**
 * 
 * 支付结果通知
 */
public interface PayNotity {
    void notify(PayResult result);
}
