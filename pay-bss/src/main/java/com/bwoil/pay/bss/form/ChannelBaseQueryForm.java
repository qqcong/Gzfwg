package com.bwoil.pay.bss.form;

import com.bwoil.common.framework.data.annotation.Condition;
import com.bwoil.common.framework.data.annotation.MatchMode;
import com.bwoil.common.framework.form.BaseQueryForm;

import lombok.Data;

@Data
public class ChannelBaseQueryForm extends BaseQueryForm {
	
	@Condition(match = MatchMode.EQ)
	private boolean enable;

}
