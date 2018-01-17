package com.bwoil.pay.bss.form;

import lombok.Data;

@Data
public class MerchantSaveForm {
	
	private Long id;
    /**
     * 商户名称
     */
    private String name;
    /**
     * 状态
     */
    private int status;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码 用于商户登录
     */
    private String password;

}
