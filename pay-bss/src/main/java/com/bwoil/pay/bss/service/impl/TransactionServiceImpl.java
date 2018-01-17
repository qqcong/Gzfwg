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
import com.bwoil.pay.bss.form.TransactionQueryForm;
import com.bwoil.pay.bss.service.TransactionService;
import com.bwoil.pay.common.dao.TransactionDao;
import com.bwoil.pay.common.entity.Transaction;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionDao transactionDao;
	
	@Override
	public PaginationResult<Transaction> listPage(TransactionQueryForm form) {
		log.info(form);
		return transactionDao.query(getAppSearchSpec(form), form);
	}
	
	private Specification<Transaction> getAppSearchSpec(TransactionQueryForm form) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>(); 
				
				if(StringUtils.isNotBlank(form.getOrderNo())) {
					predicates.add(cb.equal(root.<String>get("orderNo"),form.getOrderNo()));
				}
				
				if(StringUtils.isNotBlank(form.getStartTime())) {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date s = format.parse(form.getStartTime());
						Date e = format.parse(form.getEndTime());
						predicates.add(cb.between(root.<Date>get("created"),s,e));
					} catch (ParseException e1) {
						log.error(e1);
						e1.printStackTrace();
					}
				}
				query.orderBy(cb.desc(root.<Date>get("created")));
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();  
			}
		};
	}
}
