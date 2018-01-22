package com.bbbbb.pay.bss.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.ChannelBankQueryForm;
import com.bbbbb.pay.bss.form.ChannelBankSaveForm;
import com.bbbbb.pay.bss.service.ChannelBankService;
import com.bbbbb.pay.common.dao.BankConfigDao;
import com.bbbbb.pay.common.dao.BankDao;
import com.bbbbb.pay.common.dao.PayChannelDao;
import com.bbbbb.pay.common.entity.BankConfig;

@Service
public class ChannelBankServiceImpl implements ChannelBankService {
	
	@Autowired
	private BankConfigDao bankConfigDao;
	
	@Autowired
    private BankDao bankDao;
	
	@Autowired
	private PayChannelDao payChannelDao;

	@Override
	public PaginationResult<BankConfig> list(ChannelBankQueryForm form) {
		form.setPayChannel(payChannelDao.findById(form.getChannelCode()));
		form.setSearchText("payChannel");
		PaginationResult<BankConfig> rst = bankConfigDao.paging(form);
		return rst;
	}

	@Override
	@Transactional
	public void save(ChannelBankSaveForm form) {
		if(form.getId() != null) {
			BankConfig bankConfig = bankConfigDao.findById(form.getId());
			bankConfig.setMax(form.getMax());
			bankConfig.setMin(form.getMin());
			bankConfig.setStatus(form.getStatus());
			bankConfigDao.save(bankConfig);
			return;
		}
		BankConfig bankConfig = new BankConfig();
		BeanUtils.copyProperties(form, bankConfig);
		bankConfig.setPayChannel(payChannelDao.findById(form.getChannelCode()));
		bankConfig.setBank(bankDao.findById(form.getBankCode()));
		bankConfigDao.save(bankConfig);
		return;
	}
}
