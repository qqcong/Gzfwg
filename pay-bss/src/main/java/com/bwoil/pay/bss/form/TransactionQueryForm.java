package com.bwoil.pay.bss.form;

import com.bwoil.common.framework.form.PaginationForm;

import lombok.Data;

@Data
public class TransactionQueryForm extends PaginationForm {

	private String orderNo;

	private String startTime;

	private String endTime;

}
