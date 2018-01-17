package com.bwoil.pay.common.pay.form;

import lombok.Data;

/**
 * 解绑卡
 */
@Data
public class UnBindInterForm {
	
	/**
	 * 宝付绑卡id
	 */
	private String bindId;
	/**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 用户标识
     */
    private String identityId;


}
