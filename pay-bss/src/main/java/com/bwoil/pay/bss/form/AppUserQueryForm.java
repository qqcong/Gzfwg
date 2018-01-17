package com.bwoil.pay.bss.form;

import com.bwoil.common.framework.form.PaginationForm;

import lombok.Data;

@Data
public class AppUserQueryForm extends PaginationForm {

	private String openid;
	
	private String appid;
	
	private String phone;

	private String startTime;

	private String endTime;

}
