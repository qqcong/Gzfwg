package com.bbbbb.pay.common.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 
 * 商家,商户
 */
@Data
@Entity(name = "pay_merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 商户名称
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码 用于商户登录
     */
    private String password;

    /**
     * 商户创建时间
     */
    private Date createTime;
}
