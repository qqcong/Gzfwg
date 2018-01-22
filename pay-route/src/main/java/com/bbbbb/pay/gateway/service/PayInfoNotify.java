package com.bbbbb.pay.gateway.service;

import com.bbbbb.pay.common.entity.Transaction;
import com.bbbbb.pay.common.pay.PayNotity;

public interface PayInfoNotify extends PayNotity {

    /**
     * 支付成功回调
     * @param transaction
     */
    void notify(Transaction transaction);
}
