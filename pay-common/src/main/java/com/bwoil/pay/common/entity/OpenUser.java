package com.bwoil.pay.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chendx
 * 用户与应用之间的绑定信息
 */
@Entity(name = "pay_app_user")
@Data
public class OpenUser {

    /**
     *用户openid
     */
    @Id
    @Column(name = "openid",length = 50)
    private String openid;

    /**
     * 所属应用
     */
    @ManyToOne
    @JoinColumn(name = "appid")
    private App app;

    /**
     * 关联用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "app_user_id")
    private String appUserId;

    /**
     * 是否关注状态，已注销的为false
     */
    private boolean subscribed;

    /**
     * 创建时间
     */
    private Date createTime;
}
