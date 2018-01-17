package com.bwoil.pay.bss.service;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.TransactionQueryForm;
import com.bwoil.pay.common.entity.Transaction;

public interface TransactionService {

	PaginationResult<Transaction> listPage(TransactionQueryForm form);
	
	

}
