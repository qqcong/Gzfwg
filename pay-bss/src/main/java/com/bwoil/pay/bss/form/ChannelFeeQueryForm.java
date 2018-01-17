package com.bwoil.pay.bss.form;

import com.bwoil.common.framework.data.annotation.Condition;
import com.bwoil.common.framework.data.annotation.MatchMode;
import com.bwoil.common.framework.form.PaginationForm;
import com.bwoil.pay.common.entity.PayChannel;

import lombok.Data;

@Data
public class ChannelFeeQueryForm extends PaginationForm {

	@Condition(match = MatchMode.EQ)
	private PayChannel payChannel;

	private String channelCode;
}
