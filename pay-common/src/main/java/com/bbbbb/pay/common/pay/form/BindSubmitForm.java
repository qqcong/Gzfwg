package com.bbbbb.pay.common.pay.form;

import lombok.Data;

/**
 * 
 * 绑卡请求
 */
@Data
public class BindSubmitForm {
	
	/**
     * app Id
     */
    private String appid;
    /**
     * 请求编号
     */
    private String requestNo;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证
     */
    private String idCardNo;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户标识
     */
    private String identityId;


}
