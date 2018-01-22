package com.bbbbb.pay.bss.service.impl;

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

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.UserBindCardQueryForm;
import com.bbbbb.pay.bss.service.UserBindCardService;
import com.bbbbb.pay.bss.system.CommonTools;
import com.bbbbb.pay.common.dao.BankCardDao;
import com.bbbbb.pay.common.entity.BankCard;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class UserBindCardServiceImpl implements UserBindCardService {

	@Autowired
	BankCardDao bankCardDao;

	@Override
	public PaginationResult<BankCard> listPage(UserBindCardQueryForm form) {
		log.info(form);
		PaginationResult<BankCard> pa = bankCardDao.query(getAppSearchSpec(form), form);
		List<BankCard> list = pa.getData();
		if(list != null && !list.isEmpty()) {
			for(BankCard b: list) {
				String idCardNo = b.getIdCardNo();
				b.setIdCardNo(CommonTools.fakeIdCardNo(idCardNo));
			}
		}
		return pa;
	}

	private Specification<BankCard> getAppSearchSpec(UserBindCardQueryForm form) {
		return new Specification<BankCard>() {
			@Override
			public Predicate toPredicate(Root<BankCard> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (form.getId() != null) {
					predicates.add(cb.equal(root.<Long>get("id"), form.getId()));
				}
				if (StringUtils.isNotBlank(form.getUserName())) {
					predicates.add(cb.equal(root.<String>get("userName"), form.getUserName()));
				}
				if (StringUtils.isNotBlank(form.getMobile())) {
					predicates.add(cb.equal(root.<String>get("mobile"), form.getMobile()));
				}

				if (StringUtils.isNotBlank(form.getCardNo())) {
					predicates.add(cb.equal(root.<String>get("cardNo"), form.getCardNo()));
				}

				if (StringUtils.isNotBlank(form.getStartTime())) {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date s = format.parse(form.getStartTime());
						Date e = format.parse(form.getEndTime());
						predicates.add(cb.between(root.<Date>get("createTime"), s, e));
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
