package com.bwoil.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.MerchantQueryForm;
import com.bwoil.pay.bss.form.MerchantSaveForm;
import com.bwoil.pay.bss.service.MerchantService;
import com.bwoil.pay.common.entity.Merchant;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/merchant")
public class MerchantController {
	
	@Autowired
	MerchantService merchantService;
	
	@RequestMapping("/list")
    public PaginationResult<Merchant> list(MerchantQueryForm form){
		return merchantService.list(form);
    }
	
	@RequestMapping("/save")
    public ActionResult<?> save(MerchantSaveForm form){
		log.info(form);
		merchantService.save(form);
        return ActionResult.ok();
    }
}
