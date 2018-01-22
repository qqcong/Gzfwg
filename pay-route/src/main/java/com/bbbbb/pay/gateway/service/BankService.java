package com.bbbbb.pay.gateway.service;


import com.bbbbb.pay.gateway.form.bank.BankListForm;
import com.bbbbb.pay.gateway.result.bank.BankInfo;

import java.util.List;

public interface BankService {

     List<BankInfo> list(BankListForm form);
}
