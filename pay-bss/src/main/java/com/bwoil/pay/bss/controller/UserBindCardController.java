package com.bwoil.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.UserBindCardQueryForm;
import com.bwoil.pay.bss.service.UserBindCardService;
import com.bwoil.pay.common.entity.BankCard;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/userBindCard")
public class UserBindCardController {
	
	@Autowired
	UserBindCardService userBindCardService;
	
	@RequestMapping("/listPage")
    public PaginationResult<BankCard> listPage(UserBindCardQueryForm form) {
		return userBindCardService.listPage(form);
    }

}
