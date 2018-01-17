package com.bwoil.pay.common.constants;

/**
 * @author chendx
 * 支付订单状态
 */
public enum TradeStatus {

    /**
     * 交易创建，等待付款或确认
     */
    WAIT,
    /**
     * 系统异常
     （非终态是异常状态，出现此状态建议查询）
     */
    FAIL,
    /**
     * 交易支付成功(终态)
     */
    SUCCESS,
    /**
     * 交易结束，不可退款(终态)
     */

    FINISHED,

    /**
     * 支付处理中
     */
    PROCESSING,
    /**
     * 交易失败(终态）
     */
    ERROR;
}
