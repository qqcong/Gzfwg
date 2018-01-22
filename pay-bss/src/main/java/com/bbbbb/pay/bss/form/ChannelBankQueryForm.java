package com.bbbbb.pay.bss.form;

import com.bbbbb.common.framework.data.annotation.Condition;
import com.bbbbb.common.framework.data.annotation.MatchMode;
import com.bbbbb.common.framework.form.PaginationForm;
import com.bbbbb.pay.common.entity.PayChannel;

import lombok.Data;

@Data
public class ChannelBankQueryForm extends PaginationForm {
	
	@Condition(match = MatchMode.EQ)
	private PayChannel payChannel;
	
	private String channelCode;

}
