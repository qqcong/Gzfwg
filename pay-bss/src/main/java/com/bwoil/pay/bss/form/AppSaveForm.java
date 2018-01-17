package com.bwoil.pay.bss.form;

import lombok.Data;

@Data
public class AppSaveForm {

	private String appid;

	/**
	 * 支付公钥
	 */
	private String publicKey;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 状态 0 未初始化 1正常 2 关闭
	 */

	private Integer status;

	/**
	 * 所属商户ID
	 */
	private Long merchantId;

	/**
	 * 绑卡限制，最多能绑几张卡 0 无限制
	 */
	private Integer bindLimit;

}
