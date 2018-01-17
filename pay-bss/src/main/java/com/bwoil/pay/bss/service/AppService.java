package com.bwoil.pay.bss.service;

import java.util.List;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.AppQueryForm;
import com.bwoil.pay.bss.form.AppSaveForm;
import com.bwoil.pay.common.entity.App;

public interface AppService {
	
	PaginationResult<App> list(AppQueryForm form);
	
	public void save(AppSaveForm app);

	List<App> listAll(AppQueryForm form);

}
