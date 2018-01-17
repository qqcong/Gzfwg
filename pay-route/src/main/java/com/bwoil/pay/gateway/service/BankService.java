package com.bwoil.pay.gateway.service;


import com.bwoil.pay.gateway.form.bank.BankListForm;
import com.bwoil.pay.gateway.result.bank.BankInfo;

import java.util.List;

public interface BankService {

     List<BankInfo> list(BankListForm form);
}
