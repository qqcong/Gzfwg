package com.bwoil.pay.bss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.AppQueryForm;
import com.bwoil.pay.bss.form.AppSaveForm;
import com.bwoil.pay.bss.service.AppService;
import com.bwoil.pay.common.entity.App;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	AppService appService;
	
	@RequestMapping("/list")
    public PaginationResult<App> list(AppQueryForm form){
		log.info(form);
		return appService.list(form);
    }
	
	@RequestMapping("/listAll")
    public List<App> listAll(AppQueryForm form){
		log.info(form);
		return appService.listAll(form);
    }
	
	@RequestMapping("/save")
    public ActionResult<?> save(AppSaveForm app){
		log.info(app);
        appService.save(app);
        return ActionResult.ok();
    }
}
