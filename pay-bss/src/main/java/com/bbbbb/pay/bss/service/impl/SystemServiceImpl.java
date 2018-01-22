package com.bbbbb.pay.bss.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbbbb.pay.bss.form.SystemUserForm;
import com.bbbbb.pay.bss.service.SystemService;
import com.bbbbb.pay.bss.system.CommonTools;
import com.bbbbb.pay.common.dao.SystemUserDao;
import com.bbbbb.pay.common.entity.SystemUser;
import com.bbbbb.pay.common.util.SecurityUtil;

@Service
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	private SystemUserDao systemUserDao;
	
	@Override
	@Transactional
	public boolean login(SystemUserForm form) {
		
		SystemUser user = systemUserDao.findOneByProperty("userName", form.getUserName());
		
		if(user == null || StringUtils.equalsIgnoreCase(user.getStatus(), "N")) {
			return false;
		}
		
		String calcPassword = SecurityUtil.MD5(CommonTools.bigNumAnd(form.getPassword(), user.getSalt()));
		
		if(StringUtils.equalsIgnoreCase(user.getPassword(), calcPassword)) {
			user.setLastLogin(new Timestamp(System.currentTimeMillis()));
			systemUserDao.save(user);
			return true;
		}
		return false;
	}
	

}
