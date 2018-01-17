package com.bwoil.pay.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author chendx
 * 银行列表
 */
@Data
@Entity(name = "pay_bank")
public class Bank {

    /**
     * 银行编号
     */
    @Id
    @Column(name = "bank_code",length = 50)
    private String bankCode;
    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行logo
     */
    private String logo;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 0 未支持 1正常
     */
    private Integer status;
}
