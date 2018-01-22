package com.bbbbb.pay.bss.service;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.AppUserQueryForm;
import com.bbbbb.pay.common.entity.OpenUser;

public interface AppUserService {

	PaginationResult<OpenUser> listPage(AppUserQueryForm form);

}
