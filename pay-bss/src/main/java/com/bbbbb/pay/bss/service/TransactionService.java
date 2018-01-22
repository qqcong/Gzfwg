package com.bbbbb.pay.bss.service;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.TransactionQueryForm;
import com.bbbbb.pay.common.entity.Transaction;

public interface TransactionService {

	PaginationResult<Transaction> listPage(TransactionQueryForm form);
	
	

}
