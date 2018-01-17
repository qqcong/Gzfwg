package com.bwoil.pay.bss.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.BankQueryForm;
import com.bwoil.pay.bss.service.BankService;
import com.bwoil.pay.common.dao.BankDao;
import com.bwoil.pay.common.entity.Bank;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankDao bankDao;
    @Override
    public List<Bank> list(){
        return bankDao.findByProperty("status",1,"sort");
    }
    
    
	@Override
	public PaginationResult<Bank> listPage(BankQueryForm form) {
		form.setOrderBy("sort asc");
		PaginationResult<Bank> r = bankDao.paging(form);
		return r;
	}


	@Override
	@Transactional
	public ActionResult<Bank> save(Bank bank) {
		bankDao.save(bank);
		return ActionResult.ok();
	}
}


