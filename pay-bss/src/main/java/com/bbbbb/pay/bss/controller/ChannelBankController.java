package com.bbbbb.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbbbb.common.framework.result.ActionResult;
import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.ChannelBankQueryForm;
import com.bbbbb.pay.bss.form.ChannelBankSaveForm;
import com.bbbbb.pay.bss.service.ChannelBankService;
import com.bbbbb.pay.common.entity.BankConfig;

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
