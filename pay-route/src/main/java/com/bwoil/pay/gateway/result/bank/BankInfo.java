package com.bwoil.pay.gateway.result.bank;

import lombok.Data;

/**
 *
 */
@Data
public class BankInfo {

    private String bankCode;//	String	银行编码
    private String bankName;//	String	银行名称
    private String logo;//	String	银行LOGO
    private String max;//	String	最大单笔金额
}
