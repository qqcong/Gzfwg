package com.bwoil.pay.bss.service;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.MerchantQueryForm;
import com.bwoil.pay.bss.form.MerchantSaveForm;
import com.bwoil.pay.common.entity.Merchant;

public interface MerchantService {
	
	PaginationResult<Merchant> list(MerchantQueryForm form);
	
	public void save(MerchantSaveForm form);

}
