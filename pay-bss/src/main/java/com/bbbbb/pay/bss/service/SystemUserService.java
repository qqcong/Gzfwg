package com.bbbbb.pay.bss.service;

import com.bbbbb.common.framework.result.ActionResult;
import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.SystemUserQueryForm;
import com.bbbbb.pay.common.entity.SystemUser;

public interface SystemUserService {

	PaginationResult<SystemUser> listPage(SystemUserQueryForm form);

	ActionResult<SystemUser> save(SystemUser user);

}
