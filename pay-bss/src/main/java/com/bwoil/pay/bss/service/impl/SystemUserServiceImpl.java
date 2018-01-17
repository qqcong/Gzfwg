package com.bwoil.pay.bss.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bwoil.common.framework.result.ActionResult;
import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.SystemUserQueryForm;
import com.bwoil.pay.bss.service.SystemUserService;
import com.bwoil.pay.bss.system.CommonTools;
import com.bwoil.pay.common.dao.SystemUserDao;
import com.bwoil.pay.common.entity.SystemUser;
import com.bwoil.pay.common.util.SecurityUtil;

@Service
public class SystemUserServiceImpl implements SystemUserService {
	
	@Autowired
	SystemUserDao systemUserDao;
	
	@Override
	public PaginationResult<SystemUser> listPage(SystemUserQueryForm form) {
		form.setOrderBy("createDate desc");
		PaginationResult<SystemUser> r = systemUserDao.paging(form);
		List<SystemUser> list = r.getData();
		if(list != null && !list.isEmpty()) {
			for(SystemUser u : list ) {
				u.setSalt("");
				u.setPassword("");
			}
		}
		return r;
	}


	@Override
	@Transactional
	public ActionResult<SystemUser> save(SystemUser user) {
		if(user.getId() != null) {
			SystemUser s = systemUserDao.findById(user.getId());
			if(StringUtils.isNotBlank(user.getPassword())) {
				String uuid = UUID.randomUUID().toString().replace("-", "");
				s.setSalt(uuid);
				s.setPassword(SecurityUtil.MD5(CommonTools.bigNumAnd(user.getPassword(), uuid)));
			}
			s.setStatus(user.getStatus());
			systemUserDao.save(s);
			return ActionResult.ok();
		}
		List<SystemUser> list = systemUserDao.findByProperty("userName",user.getUserName());
		if(list != null && !list.isEmpty()) {
			return ActionResult.error("用户名已存在");
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		user.setPassword(SecurityUtil.MD5(CommonTools.bigNumAnd(user.getPassword(), uuid)));
		user.setSalt(uuid);
		systemUserDao.save(user);
		return ActionResult.ok();
	}

}
