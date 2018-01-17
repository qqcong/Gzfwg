package com.bwoil.pay.bss.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;


import com.bwoil.pay.common.util.SerialNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.AppQueryForm;
import com.bwoil.pay.bss.form.AppSaveForm;
import com.bwoil.pay.bss.service.AppService;
import com.bwoil.pay.common.dao.AppDao;
import com.bwoil.pay.common.dao.MerchantDao;
import com.bwoil.pay.common.entity.App;
import com.bwoil.pay.common.entity.Merchant;


@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private AppDao appDao;

	@Autowired
	private MerchantDao merchantDao;

	@Override
	public PaginationResult<App> list(AppQueryForm form) {
		form.setSearchFields("appName","appid");
		form.setOrderBy("createTime desc");
		PaginationResult<App> r = appDao.paging(form);
		return r;
	}
	
	@Override
	public List<App> listAll(AppQueryForm form) {
		
		List<App> r = appDao.findAll();
		return r;
	}


	@Override
	@Transactional
	public void save(AppSaveForm app) {
		App theApp = new App();
		Merchant merchant = null;
		if (app.getMerchantId() != null) {
			merchant = merchantDao.findById(app.getMerchantId());
		}

		if (StringUtils.isBlank(app.getAppid())) {
			BeanUtils.copyProperties(app, theApp);
			theApp.setAppid(SerialNumberUtils.generate());
			theApp.setCreateTime(new Date());
			theApp.setSecretKey(UUID.randomUUID().toString().replace("-", ""));
			theApp.setMerchant(merchant);
			appDao.save(theApp);
			return;
		}
		App uapp = appDao.findById(app.getAppid());
		uapp.setAppName(app.getAppName());
		uapp.setPublicKey(app.getPublicKey());
		uapp.setStatus(app.getStatus());
		uapp.setBindLimit(app.getBindLimit());
		uapp.setMerchant(merchant);
		appDao.save(uapp);
		return;
	}

}
