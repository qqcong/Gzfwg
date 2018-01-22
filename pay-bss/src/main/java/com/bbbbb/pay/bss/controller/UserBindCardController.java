package com.bbbbb.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.UserBindCardQueryForm;
import com.bbbbb.pay.bss.service.UserBindCardService;
import com.bbbbb.pay.common.entity.BankCard;

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
