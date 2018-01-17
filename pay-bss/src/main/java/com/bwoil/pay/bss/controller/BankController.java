package com.bwoil.pay.bss.controller;

import com.bwoil.pay.common.entity.Bank;

import lombok.extern.apachecommons.CommonsLog;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.BankQueryForm;
import com.bwoil.pay.bss.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *获取可用的银行列表
 */
@RestController
@RequestMapping("/bank")
@CommonsLog
public class BankController {
    @Autowired
    private BankService bankService;

    @RequestMapping("/list")
    public List<Bank> list(){
        return  bankService.list();
    }
    
    @RequestMapping("/listPage")
    public PaginationResult<Bank> listPage(BankQueryForm form){
    	return bankService.listPage(form);
    }
    
    @RequestMapping("/save")
    public ActionResult<Bank> save(Bank bank){
    	log.info(bank);
    	return bankService.save(bank);
    }
}
