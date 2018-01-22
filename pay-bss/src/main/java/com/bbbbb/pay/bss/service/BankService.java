package com.bbbbb.pay.bss.service;


import java.util.List;

import com.bbbbb.common.framework.result.ActionResult;
import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.BankQueryForm;
import com.bbbbb.pay.common.entity.Bank;

public interface BankService {

     List<Bank> list();
     
     ActionResult<Bank> save(Bank bank);
     
     PaginationResult<Bank> listPage(BankQueryForm form);
}
