package com.bbbbb.pay.bss.service;

import java.util.List;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.AppQueryForm;
import com.bbbbb.pay.bss.form.AppSaveForm;
import com.bbbbb.pay.common.entity.App;

public interface AppService {
	
	PaginationResult<App> list(AppQueryForm form);
	
	public void save(AppSaveForm app);

	List<App> listAll(AppQueryForm form);

}
