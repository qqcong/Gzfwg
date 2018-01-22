package com.bbbbb.pay.bss.form;

import com.bbbbb.common.framework.data.annotation.Condition;
import com.bbbbb.common.framework.data.annotation.MatchMode;
import com.bbbbb.common.framework.form.BaseQueryForm;

import lombok.Data;

@Data
public class ChannelBaseQueryForm extends BaseQueryForm {
	
	@Condition(match = MatchMode.EQ)
	private boolean enable;

}
