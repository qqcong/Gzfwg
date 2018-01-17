package com.bwoil.pay.bss.form;

import com.bwoil.common.framework.data.annotation.Condition;
import com.bwoil.common.framework.data.annotation.MatchMode;
import com.bwoil.common.framework.form.PaginationForm;

import lombok.Data;

@Data
public class AppQueryForm extends PaginationForm {
	
	@Condition(match = MatchMode.CONTAINS)
	private String appName;
	
	@Condition(match = MatchMode.EQ)
	private String appid;

}
