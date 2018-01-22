package com.bbbbb.pay.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * App应用
 */
@Entity(name="pay_app")
@Data
public class App {


    @Id
    @Column(name = "appid",length = 50)
    private String appid;

    /**
     * 应该用密钥
     */
    private String secretKey;
    
    /**
     * 支付公钥
     */
    private String publicKey;

    /**
     *应用名称
     */
    private String appName;

    /**
     * 状态 0 未初始化 1正常 2 关闭
     */

    private Integer status;

    /**
     * 所属商户
     */
    @ManyToOne
    private Merchant merchant;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 绑卡限制，最多能绑几张卡 0 无限制
     */
    private Integer bindLimit;
}
