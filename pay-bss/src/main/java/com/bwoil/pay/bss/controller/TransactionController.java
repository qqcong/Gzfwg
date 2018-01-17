package com.bwoil.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.TransactionQueryForm;
import com.bwoil.pay.bss.service.TransactionService;
import com.bwoil.pay.common.entity.Transaction;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping("/listPage")
    public PaginationResult<Transaction> listPage(TransactionQueryForm form) {
		return transactionService.listPage(form);
    }

}
