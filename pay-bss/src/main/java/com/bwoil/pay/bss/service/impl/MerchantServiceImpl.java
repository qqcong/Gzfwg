package com.bwoil.pay.bss.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.MerchantQueryForm;
import com.bwoil.pay.bss.form.MerchantSaveForm;
import com.bwoil.pay.bss.service.MerchantService;
import com.bwoil.pay.common.dao.MerchantDao;
import com.bwoil.pay.common.entity.Merchant;

@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;

	@Override
	public PaginationResult<Merchant> list(MerchantQueryForm form) {
		form.setSearchText("name");
		form.setOrderBy("createTime desc");
		PaginationResult<Merchant> rst = merchantDao.paging(form);
		return rst;
	}
	
	@Override
	@Transactional
	public void save(MerchantSaveForm form) {

		if (form.getId() == null) {
			Merchant merchant = new Merchant();
			BeanUtils.copyProperties(form, merchant);
			merchant.setCreateTime(new Date());
			merchantDao.save(merchant);
			return;
		}
		Merchant merchant = merchantDao.findById(form.getId());
		merchant.setUserName(form.getUserName());
		merchant.setPassword(form.getPassword());
		merchant.setName(form.getName());
		merchant.setStatus(form.getStatus());
		merchantDao.save(merchant);
		return;
	}

}
