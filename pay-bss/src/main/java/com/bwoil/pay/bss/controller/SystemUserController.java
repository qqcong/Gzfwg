package com.bwoil.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.SystemUserQueryForm;
import com.bwoil.pay.bss.service.SystemUserService;
import com.bwoil.pay.common.entity.SystemUser;

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
