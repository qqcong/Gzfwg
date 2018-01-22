package com.bbbbb.pay.common.entity;

import lombok.Data;


import javax.persistence.*;
import java.util.Date;

/**
 * 
 * 绑卡信息
 */
@Data
@Entity(name = "pay_bind_record")
public class BindRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 银行卡
     */
    @ManyToOne
    @JoinColumn(name = "card_id")
    private BankCard card;
    /**
     * 关联用户
     */
    @ManyToOne
    @JoinColumn(name = "open_user_id")
    private OpenUser openUser;
    /**
     * 绑定渠道
     */
    @ManyToOne
    @JoinColumn(name = "channel_config")
    private ChannelConfig channelConfig;

    /**
     * 绑卡时间
     */
    private Date createTime;
    /**
     * 绑卡请求Id
     */
    private String requestId;

    /**
     * 第三方系统绑定编号
     */
    private String bindId;

    /**
     * 状态 0 未验证 1验证通过 2.验证失败 3.已解绑
     */
    private int  status;

    /**
     * 失败编号
     */
    private String errorCode;

    /**
     * 失败原因
     */
    private String errorMsg;

}
