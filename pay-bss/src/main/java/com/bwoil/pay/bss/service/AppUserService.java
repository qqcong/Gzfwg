package com.bwoil.pay.bss.service;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.AppUserQueryForm;
import com.bwoil.pay.common.entity.OpenUser;

public interface AppUserService {

	PaginationResult<OpenUser> listPage(AppUserQueryForm form);

}
