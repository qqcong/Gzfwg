package com.bbbbb.pay.gateway.controller;

import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.common.entity.App;
import com.bbbbb.pay.gateway.form.SignForm;
import com.bbbbb.pay.gateway.form.bank.BankListForm;
import com.bbbbb.pay.gateway.result.ListDataResult;
import com.bbbbb.pay.gateway.result.SignResult;
import com.bbbbb.pay.gateway.result.bank.BankInfo;
import com.bbbbb.pay.gateway.service.AppService;
import com.bbbbb.pay.gateway.service.BankService;
import com.bbbbb.pay.gateway.util.SignUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * 
 * 跟银行信相关API
 */

@RestController
@RequestMapping("/api/bank")
@CommonsLog
public class BankController extends BaseController {
    @Autowired
    private BankService bankService;

    /**
     *获取可用的银行列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public SignResult list(@RequestBody @Valid BankListForm form) {
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
             SignUtils.sign(errResult);
            return errResult;
        }
        List<BankInfo> ns= bankService.list(form);
        ListDataResult result=new ListDataResult();
        result.setAppid(form.getAppid());
        result.setData(JsonUtils.toString(ns));
        SignUtils.sign(result);
        return  result;
    }


}
