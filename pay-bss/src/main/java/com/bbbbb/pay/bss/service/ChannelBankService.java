package com.bbbbb.pay.bss.service;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.ChannelBankQueryForm;
import com.bbbbb.pay.bss.form.ChannelBankSaveForm;
import com.bbbbb.pay.common.entity.BankConfig;

public interface ChannelBankService {

	void save(ChannelBankSaveForm form);

	PaginationResult<BankConfig> list(ChannelBankQueryForm form);

}
