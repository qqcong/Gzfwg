package com.bwoil.pay.bss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.common.framework.result.QueryResult;
import com.bwoil.pay.bss.form.ChannelQueryForm;
import com.bwoil.pay.bss.form.ChannelSaveForm;
import com.bwoil.pay.bss.service.ChannelService;
import com.bwoil.pay.common.entity.PayChannel;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/channel")
public class ChannelController {
	
	@Autowired
	ChannelService channelService;
	
	@RequestMapping("/list")
    public PaginationResult<PayChannel> listChannel(ChannelQueryForm form) {
		log.info(form);
		return channelService.list(form);
    }
	
	@RequestMapping("/listAll")
    public QueryResult<PayChannel> list() {
		return channelService.list();
    }
	
	@RequestMapping("/save")
    public ActionResult<?> save(ChannelSaveForm form) {
		log.info(form);
		channelService.save(form);
        return ActionResult.ok();
    }

}
