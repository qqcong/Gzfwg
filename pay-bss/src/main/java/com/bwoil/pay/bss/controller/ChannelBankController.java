package com.bwoil.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.ChannelBankQueryForm;
import com.bwoil.pay.bss.form.ChannelBankSaveForm;
import com.bwoil.pay.bss.service.ChannelBankService;
import com.bwoil.pay.common.entity.BankConfig;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/channelBank")
public class ChannelBankController {
	
	@Autowired
	ChannelBankService channelBankService;
	
	@RequestMapping("/list")
    public PaginationResult<BankConfig> listChannel(ChannelBankQueryForm form) {
		log.info(form);
		return channelBankService.list(form);
    }
	
	@RequestMapping("/save")
    public ActionResult<?> save(ChannelBankSaveForm form) {
		log.info(form);
		channelBankService.save(form);
        return ActionResult.ok();
    }

}
