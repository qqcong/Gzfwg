package com.bbbbb.pay.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * 绑定请求信息
 */
@Data
@Entity(name = "pay_bind_order")
public class BindOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "appid")
    private App app;
    /**
     * 绑定的用户id,来自己业务系统
     */
    private String userId;
    /**
     * 卡号所属银行
     */
    @ManyToOne
    @JoinColumn(name = "bank_code")
    private Bank bank;
    /**
     * 绑卡请求Id
     */
    private String requestId;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 用户姓名
     */
    private String name;
    /**
     *银行预留手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     *验证渠道
     */
    @ManyToOne
    @JoinColumn(name = "channel_config")
    private ChannelConfig channel;
    /**
     *创建时间
     */
    private Date createTime;
    /**
     *绑定状态 0未提交 1.已提交未确认 2.绑卡成功 3.绑卡失败 4.绑卡请求超时
     */
    private int status;


    /**
     * 成功后绑定id
     */
    private String bindId;
}
