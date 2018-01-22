package com.bbbbb.pay.common.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 
 * 渠道银行限额
 */
@Data
@Entity(name = "pay_channel_bank")
public class BankConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支付渠道
     */
    @ManyToOne
    @JoinColumn(name = "channel_code")
    private PayChannel payChannel;
    /**
     * 银行
     */
    @ManyToOne
    @JoinColumn(name = "bank_code")
    private  Bank bank;
    /**
     *最小金额
     */
    private Integer min;
    /**
     * 最大金额
     */
    private Integer max;

    /**
     * 状态 0 未开通 1 正常 2 关闭 3.异常
     */
    @Column(name = "status")
    private Integer status;
}
