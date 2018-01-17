package com.bwoil.pay.common.pay;

import com.bwoil.pay.common.pay.form.PayForm;
import com.bwoil.pay.common.pay.result.PayResult;

import java.util.Map;

/**
 *@author chendx
 * 支付服务接口定义
 */
public interface Pay {

    /**
     * 发起支付请求
     * @param payForm
     * @return 支付结果和状态信息
     */
    PayResult pay(PayForm payForm);

    /**
     * 查询支付订单信息
     * @param orderId  商家订单号
     * @param transactionNo 第三支付流水号
     * @return 支付结果和状态信息
     */
    PayResult query(String orderId,String transactionNo);

    /**
     * 设置参数
     * @param config
     */
    void setConfig(Map<String,String> config);
    /**
     * 检查配置信息
     * 当配置有误抛出系统异常
     * @throws SystemException
     */
    void checkConfig(Map<String, String> config) throws SystemException;
}
