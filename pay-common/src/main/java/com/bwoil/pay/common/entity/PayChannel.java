package com.bwoil.pay.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chendx
 * 支付通道
 */
@Data
@Entity(name="pay_channel")
public class PayChannel {

    /**
     * 通道编号
     */
    @Column(name = "channel_code",length = 50)
    @Id
    private String code;
    /**
     * 通道名称
     */
    private String name;

    /**
     * 是否开启
     */
    private boolean enable;

    /**
     * 渠道状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 绑卡实现类
     */
    private String bindClass;


    /**
     * 支付实现类
     */
    private String payClass;
}
