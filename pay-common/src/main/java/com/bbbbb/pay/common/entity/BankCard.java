package com.bbbbb.pay.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "pay_bank_card")
@EqualsAndHashCode(exclude = "id")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;

    /**
     * 身份证
     */
    private String idCardNo;
    /**
     * 卡号所属银行
     */
    @ManyToOne
    @JoinColumn(name = "bank_code")
    private Bank bank;

    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     *银行预留手机号
     */
    private String mobile;


    /**
     * 0 已删除 1正常  2已解绑
     */
    private int status;


    private Date createTime;

    /**
     * 最后使用时间
     */
    private Date lastUsed;

    /**
     * 使用次数
     */
    private int usedCount;

    /**
     * 是否是默认支付卡
     */
    private boolean defaultCard;

    /**
     * 内部绑定编号
     */
    private String bindId;

    /**
     * 银行卡前6位
     * @return
     */
    @Transient
    public String getTopCode(){
        return StringUtils.leftPad(getCardNo(),6);
    }


    /**
     * 银行卡后4位
     * @return
     */
    @Transient
    public String getLastCode(){
        return StringUtils.rightPad(getCardNo(),4);
    }
}
