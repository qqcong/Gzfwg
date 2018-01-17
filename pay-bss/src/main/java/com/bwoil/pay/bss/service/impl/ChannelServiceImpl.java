package com.bwoil.pay.bss.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.common.framework.result.QueryResult;
import com.bwoil.pay.bss.form.ChannelBaseQueryForm;
import com.bwoil.pay.bss.form.ChannelQueryForm;
import com.bwoil.pay.bss.form.ChannelSaveForm;
import com.bwoil.pay.bss.service.ChannelService;
import com.bwoil.pay.common.dao.PayChannelDao;
import com.bwoil.pay.common.entity.PayChannel;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private PayChannelDao payChannelDao;

	@Override
	public PaginationResult<PayChannel> list(ChannelQueryForm form) {
		form.setSearchText("name");
		form.setOrderBy("createTime desc");
		PaginationResult<PayChannel> rst = payChannelDao.paging(form);
		return rst;
	}
	
	@Override
	public QueryResult<PayChannel> list() {
		ChannelBaseQueryForm form = new ChannelBaseQueryForm();
		form.setEnable(true);
		form.setSearchFields("enable");
		QueryResult<PayChannel> rst = payChannelDao.query(form);
		return rst;
	}

	@Override
	@Transactional
	public void save(ChannelSaveForm form) {

		PayChannel channel = payChannelDao.findById(form.getCode());
		if (channel == null) {
			channel = new PayChannel();
			channel.setCreateTime(new Date());
		}
		BeanUtils.copyProperties(form, channel);
		payChannelDao.save(channel);
		return;
	}

}
