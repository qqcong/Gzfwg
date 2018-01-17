package com.bwoil.pay.gateway.service;

import com.bwoil.pay.common.entity.Transaction;
import com.bwoil.pay.common.pay.PayNotity;

public interface PayInfoNotify extends PayNotity {

    /**
     * 支付成功回调
     * @param transaction
     */
    void notify(Transaction transaction);
}
