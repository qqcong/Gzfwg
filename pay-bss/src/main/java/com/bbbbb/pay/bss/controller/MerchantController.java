package com.bbbbb.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbbbb.common.framework.result.ActionResult;
import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.MerchantQueryForm;
import com.bbbbb.pay.bss.form.MerchantSaveForm;
import com.bbbbb.pay.bss.service.MerchantService;
import com.bbbbb.pay.common.entity.Merchant;

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
