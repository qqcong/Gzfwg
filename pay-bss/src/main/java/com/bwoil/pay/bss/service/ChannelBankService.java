package com.bwoil.pay.bss.service;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.ChannelBankQueryForm;
import com.bwoil.pay.bss.form.ChannelBankSaveForm;
import com.bwoil.pay.common.entity.BankConfig;

public interface ChannelBankService {

	void save(ChannelBankSaveForm form);

	PaginationResult<BankConfig> list(ChannelBankQueryForm form);

}
