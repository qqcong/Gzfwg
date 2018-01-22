package com.bbbbb.pay.bss.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.ChannelFeeQueryForm;
import com.bbbbb.pay.bss.form.ChannelFeeSaveForm;
import com.bbbbb.pay.bss.service.ChannelFeeService;
import com.bbbbb.pay.common.dao.AppDao;
import com.bbbbb.pay.common.dao.ChannelConfigDao;
import com.bbbbb.pay.common.dao.PayChannelDao;
import com.bbbbb.pay.common.entity.ChannelConfig;

@Service
public class ChannelFeeServiceImpl implements ChannelFeeService {
	
	@Autowired
	private ChannelConfigDao channelConfigDao;
	
	@Autowired
	private PayChannelDao payChannelDao;
	
	@Autowired
	private AppDao appDao;

	@Override
	public PaginationResult<ChannelConfig> list(ChannelFeeQueryForm form) {
		form.setPayChannel(payChannelDao.findById(form.getChannelCode()));
		form.setSearchText("payChannel");
		PaginationResult<ChannelConfig> rst = channelConfigDao.paging(form);
		return rst;
	}

	@Override
	@Transactional
	public void save(ChannelFeeSaveForm form) {
		ChannelConfig channelConfig = new ChannelConfig();
		if(form.getId() != null) {
			channelConfig = channelConfigDao.findById(form.getId());
		}
		BeanUtils.copyProperties(form, channelConfig);
		if(StringUtils.isNotBlank(form.getChannelCode())) {
			channelConfig.setPayChannel(payChannelDao.findById(form.getChannelCode()));
		}
		if(StringUtils.isNotBlank(form.getAppid())) {
			channelConfig.setApp(appDao.findById(form.getAppid()));
		}
		if(StringUtils.isNotBlank(form.getConfig())) {
			Map<String,String> config = JSON.parseObject(form.getConfig(), new TypeReference<Map<String,String>>() {});
			channelConfig.setConfig(config);
		}
		channelConfigDao.save(channelConfig);
	}

}
