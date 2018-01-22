package com.bbbbb.pay.bss.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbbbb.common.framework.result.ActionResult;
import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.BankQueryForm;
import com.bbbbb.pay.bss.service.BankService;
import com.bbbbb.pay.common.dao.BankDao;
import com.bbbbb.pay.common.entity.Bank;

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


