package com.bwoil.pay.bss.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.AppUserQueryForm;
import com.bwoil.pay.bss.service.AppUserService;
import com.bwoil.pay.common.dao.AppDao;
import com.bwoil.pay.common.dao.OpenUserDao;
import com.bwoil.pay.common.entity.App;
import com.bwoil.pay.common.entity.OpenUser;
import com.bwoil.pay.common.entity.User;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class AppUserServiceImpl implements AppUserService {
	
	@Autowired
	OpenUserDao openUserDao;
	
	@Autowired
	AppDao appDao;
	
	@Override
	public PaginationResult<OpenUser> listPage(AppUserQueryForm form) {
		log.info(form);
		return openUserDao.query(getAppSearchSpec(form), form);
	}
	
	private Specification<OpenUser> getAppSearchSpec(AppUserQueryForm form) {
		return new Specification<OpenUser>() {
			@Override
			public Predicate toPredicate(Root<OpenUser> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(form.getOpenid())) {
					predicates.add(cb.equal(root.<String>get("openid"),form.getOpenid()));
				}
				
				if(StringUtils.isNotBlank(form.getAppid())) {
					App app = appDao.findById(form.getAppid());
					predicates.add(cb.equal(root.<String>get("app"),app));
				}
				
				if(StringUtils.isNotBlank(form.getPhone())) {
					predicates.add(cb.equal(root.<User>get("user").get("phone"),form.getPhone()));
				}
				
				if(StringUtils.isNotBlank(form.getStartTime())) {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date s = format.parse(form.getStartTime());
						Date e = format.parse(form.getEndTime());
						predicates.add(cb.between(root.<Date>get("createTime"),s,e));
					} catch (ParseException e1) {
						log.error(e1);
						e1.printStackTrace();
					}
				}
				query.orderBy(cb.desc(root.<Date>get("createTime")));
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();  
			}
		};
	}

}
