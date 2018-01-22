package com.bbbbb.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbbbb.common.framework.result.ActionResult;
import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.SystemUserQueryForm;
import com.bbbbb.pay.bss.service.SystemUserService;
import com.bbbbb.pay.common.entity.SystemUser;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/systemUser")
public class SystemUserController {
	
	@Autowired
	SystemUserService systemUserService;
	
	@RequestMapping("/listPage")
    public PaginationResult<SystemUser> listPage(SystemUserQueryForm form){
    	return systemUserService.listPage(form);
    }
    
    @RequestMapping("/save")
    public ActionResult<SystemUser> save(SystemUser user){
    	return systemUserService.save(user);
    }

}
