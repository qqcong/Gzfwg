package com.bbbbb.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.AppUserQueryForm;
import com.bbbbb.pay.bss.service.AppUserService;
import com.bbbbb.pay.common.entity.OpenUser;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/appUser")
public class AppUserController {
	
	@Autowired
	AppUserService appUserService;
	
	@RequestMapping("/listPage")
    public PaginationResult<OpenUser> listPage(AppUserQueryForm form) {
		return appUserService.listPage(form);
    }

}
