package com.bwoil.pay.bss.system;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.bwoil.common.framework.result.ActionResult;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		log.info("preHandle...");
		String uri = request.getRequestURI();
		log.info("preHandle...uri: "+uri);
		if(uri.contains("/system/")) {
			return true;
		} else {
			String user = (String) request.getSession().getAttribute("user");
			log.info("preHandle...user:"+user);
			if(StringUtils.isNotBlank(user)) {
				return true;
			}
		}
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		OutputStream out;
		try {
			out = response.getOutputStream();
			out.write(JSON.toJSONString(ActionResult.error("timeout")).getBytes("utf-8"));
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("preHandles",e);
		}
		return false;
	}

}
