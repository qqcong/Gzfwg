package com.bwoil.pay.gateway.service;

import com.bwoil.pay.gateway.form.pay.FirstPayForm;
import com.bwoil.pay.gateway.form.pay.PayComfirmForm;
import com.bwoil.pay.gateway.form.pay.PaySubmitForm;
import com.bwoil.pay.gateway.form.pay.QueryPayForm;
import com.bwoil.pay.gateway.result.pay.PayOrderInfo;
import com.bwoil.pay.gateway.result.pay.PaySubmitResult;

/**
 * @author chendx
 * 支付服务
 */
public interface PayService {

    /**
     * 预支付
     * @param form
     * @return
     */
    PaySubmitResult submit(PaySubmitForm form);

    /**
     * 支付确认
     * @param form
     * @return
     */
    PayOrderInfo comfirm(PayComfirmForm form);

    /**
     * 支付结果查询
     * @param form
     * @return
     */
    PayOrderInfo query(QueryPayForm form);


    /**
     * 首次支付
     * @param form
     * @return
     */
    PaySubmitResult firstPay(FirstPayForm form);

}
