package com.bbbbb.pay.bss.form;

import com.bbbbb.common.framework.data.annotation.Condition;
import com.bbbbb.common.framework.data.annotation.MatchMode;
import com.bbbbb.common.framework.form.PaginationForm;

import lombok.Data;

@Data
public class ChannelQueryForm extends PaginationForm {
	
	@Condition(match = MatchMode.CONTAINS)
	private String name;

}
