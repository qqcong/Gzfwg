package com.bwoil.pay.gateway.controller;

import com.bwoil.common.framework.util.JsonUtils;
import com.bwoil.pay.common.entity.App;
import com.bwoil.pay.gateway.form.SignForm;
import com.bwoil.pay.gateway.form.bank.BankListForm;
import com.bwoil.pay.gateway.result.ListDataResult;
import com.bwoil.pay.gateway.result.SignResult;
import com.bwoil.pay.gateway.result.bank.BankInfo;
import com.bwoil.pay.gateway.service.AppService;
import com.bwoil.pay.gateway.service.BankService;
import com.bwoil.pay.gateway.util.SignUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * @author chendx
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
