package com.bwoil.pay.gateway.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bwoil.common.framework.util.IDCardUtils;
import com.bwoil.pay.common.pay.SystemException;
import com.bwoil.pay.gateway.form.pay.FirstPayForm;
import com.bwoil.pay.gateway.form.pay.PayComfirmForm;
import com.bwoil.pay.gateway.form.pay.PaySubmitForm;
import com.bwoil.pay.gateway.form.pay.QueryPayForm;
import com.bwoil.pay.gateway.result.SignResult;
import com.bwoil.pay.gateway.result.pay.PayOrderInfo;
import com.bwoil.pay.gateway.result.pay.PaySubmitResult;
import com.bwoil.pay.gateway.service.PayService;
import com.bwoil.pay.gateway.util.SignUtils;

@RestController
@RequestMapping("/api/fast/pay")
public class FastPayController extends BaseController{

    @Autowired
    private PayService payService;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public SignResult submit(@Valid @RequestBody PaySubmitForm form){
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
            SignUtils.sign(errResult);
            return errResult;
        }
        PaySubmitResult result= payService.submit(form);
        result.setAppid(form.getAppid());
        SignUtils.sign(result);
        return result;

    }
    @RequestMapping(value = "/comfirm",method = RequestMethod.POST)
    public SignResult comfirm(@Valid @RequestBody  PayComfirmForm form){
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
            SignUtils.sign(errResult);
            return errResult;
        }
        PayOrderInfo result= payService.comfirm(form);
        result.setAppid(form.getAppid());
        SignUtils.sign(result);
        return result;

    }


    @RequestMapping(value = "/first",method = RequestMethod.POST)
    public SignResult firstPay(@Valid @RequestBody  FirstPayForm form){
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
            SignUtils.sign(errResult);
            return errResult;
        }
        if(!IDCardUtils.idCardValidate(form.getIdCardNo())){
            throw new SystemException("BW00321","身份证不正确");
        }
        PaySubmitResult result= payService.firstPay(form);
        result.setAppid(form.getAppid());
        SignUtils.sign(result);
        return result;



    }
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public  SignResult query(@Valid  @RequestBody  QueryPayForm form){
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
            SignUtils.sign(errResult);
            return errResult;
        }
        PayOrderInfo result= payService.query(form);
        result.setAppid(form.getAppid());
        SignUtils.sign(result);
        return result;
    }
}
