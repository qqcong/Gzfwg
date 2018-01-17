package com.bwoil.pay.gateway.controller;

import com.bwoil.common.framework.util.JsonUtils;
import com.bwoil.pay.common.entity.App;
import com.bwoil.pay.common.util.ErrorsUtils;
import com.bwoil.pay.gateway.form.SignForm;
import com.bwoil.pay.gateway.result.SignResult;
import com.bwoil.pay.gateway.service.AppService;
import com.bwoil.pay.gateway.util.SignUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author chendx
 * 支付API Controller 基类
 */
@CommonsLog
public abstract class BaseController {
    @Autowired
    protected AppService appService;

    /**
     *是否做签名检查
     */
    @Value("${pay.sign.check}")
    private  boolean enableSign=false;

    protected SignResult checkFormSign(SignForm form){

        log.info("支付系统收到请求,正在验证签名"+ JsonUtils.toString(form));
        if(!enableSign) {
            log.warn("系统关闭签名验证");
            return null;
        }
        SignResult result=new SignResult();
        result.setAppid(form.getAppid());
        App app=appService.getApp(form.getAppid());

        if(app==null){
            result.setCode("BW00123");
            result.setMsg("应用appid["+form.getAppid()+"]不存在,请检查配置");
            return result;
        }else {
            String publickey = app.getPublicKey();
            try {
                SignUtils.checkSign(form, publickey);
                log.info("签名正确");
                return null;
            } catch (Exception e) {
                log.error("签名错误", e);

            }
        }

        result.setCode("BW00010");
        result.setMsg(ErrorsUtils.getMessage("BW00010"));
        return result;
    }
}
