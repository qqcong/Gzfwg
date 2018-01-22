package com.bbbbb.pay.bss.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbbbb.common.framework.result.ActionResult;
import com.bbbbb.pay.bss.form.SystemUserForm;
import com.bbbbb.pay.bss.service.SystemService;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/system")
public class SystemController {
	
	@Autowired
	SystemService systemService;
	
	@RequestMapping("/login")
    public ActionResult<?> login(HttpServletRequest request, SystemUserForm form) {
		if(systemService.login(form)) {
			request.getSession().setAttribute("user", form.getUserName());
			return ActionResult.ok();
		}
		return ActionResult.error("用户名或密码错误");
    }
	
	@RequestMapping("/logout")
    public ActionResult<?> logout(HttpServletRequest request) {
		request.getSession().removeAttribute("user");
		return ActionResult.ok();
    }
	
	@RequestMapping("/checkLogin")
    public ActionResult<String> checkLogin(HttpServletRequest request) {
		String user = (String)request.getSession().getAttribute("user");
		if(StringUtils.isNotBlank(user)) {
			return ActionResult.ok(user);
		}
		return ActionResult.error();
    }

}
