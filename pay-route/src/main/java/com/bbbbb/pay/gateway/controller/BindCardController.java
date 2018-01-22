package com.bbbbb.pay.gateway.controller;


import com.bbbbb.common.framework.util.IDCardUtils;
import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.common.pay.PayException;
import com.bbbbb.pay.common.pay.SystemException;
import com.bbbbb.pay.common.pay.result.BindResult;
import com.bbbbb.pay.gateway.form.bind.*;
import com.bbbbb.pay.gateway.result.ListDataResult;
import com.bbbbb.pay.gateway.result.SignResult;
import com.bbbbb.pay.gateway.result.bind.BindCardInfo;
import com.bbbbb.pay.gateway.result.bind.BindComfirmResult;
import com.bbbbb.pay.gateway.result.bind.BindSubmitResult;
import com.bbbbb.pay.gateway.result.bind.UnbindResult;
import com.bbbbb.pay.gateway.service.BindService;
import com.bbbbb.pay.gateway.util.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 
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
