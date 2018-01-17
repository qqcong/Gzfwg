package com.bwoil.pay.gateway.controller;


import com.bwoil.common.framework.util.IDCardUtils;
import com.bwoil.common.framework.util.JsonUtils;
import com.bwoil.pay.common.pay.PayException;
import com.bwoil.pay.common.pay.SystemException;
import com.bwoil.pay.common.pay.result.BindResult;
import com.bwoil.pay.gateway.form.bind.*;
import com.bwoil.pay.gateway.result.ListDataResult;
import com.bwoil.pay.gateway.result.SignResult;
import com.bwoil.pay.gateway.result.bind.BindCardInfo;
import com.bwoil.pay.gateway.result.bind.BindComfirmResult;
import com.bwoil.pay.gateway.result.bind.BindSubmitResult;
import com.bwoil.pay.gateway.result.bind.UnbindResult;
import com.bwoil.pay.gateway.service.BindService;
import com.bwoil.pay.gateway.util.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author chendx
 * 绑卡相关api
 */
@RestController
@RequestMapping("/api/fast/bind")
public class BindCardController extends BaseController{


    @Autowired
    private BindService bindService;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public SignResult submit(@Valid @RequestBody BindSubmitForm form){
          SignResult errResult = checkFormSign(form);
          if (errResult != null) {
              SignUtils.sign(errResult);
              return errResult;
          }
        if(!IDCardUtils.idCardValidate(form.getIdCardNo())){
              throw new SystemException("BW00321","身份证不正确");
        }


        BindSubmitResult result= bindService.submit(form);
        result.setAppid(form.getAppid());
        SignUtils.sign(result);
        return result;

    }
    @RequestMapping(value = "/comfirm",method = RequestMethod.POST)
    public SignResult comfirm(@Valid @RequestBody   BindComfirmForm form){
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
            SignUtils.sign(errResult);
            return errResult;
        }
        BindComfirmResult result= bindService.comfirm(form);
        result.setAppid(form.getAppid());
        SignUtils.sign(result);
        return result;

    }


    @RequestMapping(value = "/unbind",method = RequestMethod.POST)
    public SignResult unbind(@Valid @RequestBody  UnbindForm form){
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
            SignUtils.sign(errResult);
            return errResult;
        }
        UnbindResult result= bindService.unbind(form);
        result.setAppid(form.getAppid());
        SignUtils.sign(result);
        return result;



    }
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public  SignResult query(@Valid @RequestBody  QueryBindForm form){
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
            SignUtils.sign(errResult);
            return errResult;
        }
        BindCardInfo result= bindService.query(form);
        result.setAppid(form.getAppid());
        SignUtils.sign(result);
        return result;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public SignResult list(@Valid @RequestBody BindListForm form){
        SignResult errResult = checkFormSign(form);
        if (errResult != null) {
            SignUtils.sign(errResult);
            return errResult;
        }
        List<BindCardInfo> result= bindService.list(form);
        ListDataResult listDataResult=new ListDataResult();

        listDataResult.setAppid(form.getAppid());
        listDataResult.setData(JsonUtils.toString(result));
        SignUtils.sign(listDataResult);
        return listDataResult;
    }
}
