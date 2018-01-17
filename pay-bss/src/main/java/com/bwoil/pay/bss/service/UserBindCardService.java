package com.bwoil.pay.bss.service;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.UserBindCardQueryForm;
import com.bwoil.pay.common.entity.BankCard;

public interface UserBindCardService {
	
	PaginationResult<BankCard> listPage(UserBindCardQueryForm form);

}
