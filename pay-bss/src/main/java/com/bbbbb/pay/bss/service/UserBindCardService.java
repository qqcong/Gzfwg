package com.bbbbb.pay.bss.service;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.UserBindCardQueryForm;
import com.bbbbb.pay.common.entity.BankCard;

public interface UserBindCardService {
	
	PaginationResult<BankCard> listPage(UserBindCardQueryForm form);

}
