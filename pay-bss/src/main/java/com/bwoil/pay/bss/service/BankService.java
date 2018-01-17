package com.bwoil.pay.bss.service;


import java.util.List;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.BankQueryForm;
import com.bwoil.pay.common.entity.Bank;

public interface BankService {

     List<Bank> list();
     
     ActionResult<Bank> save(Bank bank);
     
     PaginationResult<Bank> listPage(BankQueryForm form);
}
