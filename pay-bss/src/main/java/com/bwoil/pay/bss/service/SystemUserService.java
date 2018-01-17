package com.bwoil.pay.bss.service;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.SystemUserQueryForm;
import com.bwoil.pay.common.entity.SystemUser;

public interface SystemUserService {

	PaginationResult<SystemUser> listPage(SystemUserQueryForm form);

	ActionResult<SystemUser> save(SystemUser user);

}
