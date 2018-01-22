package com.bbbbb.pay.bss.service;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.MerchantQueryForm;
import com.bbbbb.pay.bss.form.MerchantSaveForm;
import com.bbbbb.pay.common.entity.Merchant;

public interface MerchantService {
	
	PaginationResult<Merchant> list(MerchantQueryForm form);
	
	public void save(MerchantSaveForm form);

}
