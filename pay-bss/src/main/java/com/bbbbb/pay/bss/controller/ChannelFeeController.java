package com.bbbbb.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbbbb.common.framework.result.ActionResult;
import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.ChannelFeeQueryForm;
import com.bbbbb.pay.bss.form.ChannelFeeSaveForm;
import com.bbbbb.pay.bss.service.ChannelFeeService;
import com.bbbbb.pay.common.entity.ChannelConfig;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/channelFee")
public class ChannelFeeController {
	
	@Autowired
	private ChannelFeeService channelFeeService;
	
	@RequestMapping("/list")
    public PaginationResult<ChannelConfig> listChannel(ChannelFeeQueryForm form) {
		log.info(form);
		return channelFeeService.list(form);
    }
	
	@RequestMapping("/save")
    public ActionResult<?> save(ChannelFeeSaveForm form) {
		log.info(form);
		channelFeeService.save(form);
        return ActionResult.ok();
    }
	

}
