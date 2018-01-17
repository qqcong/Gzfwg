package com.bwoil.pay.bss.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bwoil.common.framework.result.ActionResult;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ActionResult<?> jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		log.error(e);
		log.error(req.getRequestURI());
		log.error(JSON.toJSONString(req.getParameterMap()));
		return ActionResult.error(e.getMessage());
    }

}
