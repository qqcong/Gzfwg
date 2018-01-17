package com.bwoil.pay.gateway.controller;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.pay.common.pay.PayException;
import com.bwoil.pay.common.pay.SystemException;
import com.bwoil.pay.common.util.ErrorsUtils;
import com.bwoil.pay.gateway.result.SignResult;
import com.bwoil.pay.gateway.util.SignUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.validation.BindException;


import java.util.Set;

/**
 * @author chendx
 * 统一处理请异常求
 */
@ControllerAdvice
@CommonsLog
public class ExceptionHandle {

    /**
     * 处理校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseBody
    public ActionResult handler(ConstraintViolationException  e){

        Set<ConstraintViolation<?>> cvs=    e.getConstraintViolations();
        StringBuffer sb=new StringBuffer();
        cvs.forEach(v ->{
            sb.append(v.getMessage());
        });
        return ActionResult.error(10000,sb.toString());
    }

    /**
     * 处理数据绑定异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = { BindException.class })
    @ResponseBody
    public ActionResult handler(BindException  e){

        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return  ActionResult.error(10000,message);
    }

    /**
     * 处理数据绑定异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseBody
    public SignResult handler(MethodArgumentNotValidException exception){

        BindingResult result = exception.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);

        SignResult r=new SignResult();
        r.setCode("BW00009");
        r.setMsg(message);
        SignUtils.sign(r);
        return  r;

    }

    /**
     * 处理数据绑定异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = { SystemException.class })
    @ResponseBody
    public SignResult handler(SystemException exception){
        SignResult result=new SignResult();
        result.setCode(exception.getCode());
        result.setMsg(exception.getMessage());
        SignUtils.sign(result);
        return  result;
    }


    /**
     * 处理数据绑定异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = { PayException.class })
    @ResponseBody
    public SignResult handler(PayException exception){
        SignResult result=new SignResult();
        result.setCode(exception.getCode());
        result.setMsg(exception.getMessage());
        SignUtils.sign(result);
        return  result;
    }
    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    @ResponseBody
    public SignResult handler(HttpMessageNotReadableException exception){
        SignResult result=new SignResult();
        result.setCode("BW00009");
        result.setMsg("包含不支持的参数，请检查");
        SignUtils.sign(result);
        return  result;
    }


    /**
     *处理其它系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    public SignResult handler(Exception  e){

        log.error("系统异常",e);
        SignResult result=new SignResult();
        result.setCode("BW00100");
        result.setMsg(ErrorsUtils.getMessage("BW00100"));
        SignUtils.sign(result);
        return  result;
    }
}
