package com.bbbbb.pay.bss.form;

import com.bbbbb.common.framework.data.annotation.Condition;
import com.bbbbb.common.framework.data.annotation.MatchMode;
import com.bbbbb.common.framework.form.PaginationForm;

import lombok.Data;

@Data
public class UserBindCardQueryForm extends PaginationForm {

	@Condition(match = MatchMode.EQ)
	private String userName;
	
	@Condition(match = MatchMode.EQ)
	private Long id;
	
	@Condition(match = MatchMode.EQ)
	private String mobile;
	
	@Condition(match = MatchMode.EQ)
	private String cardNo;
	
	private String startTime;
	
	private String endTime;

}
