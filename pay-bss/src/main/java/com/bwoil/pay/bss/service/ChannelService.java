package com.bwoil.pay.bss.service;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.common.framework.result.QueryResult;
import com.bwoil.pay.bss.form.ChannelQueryForm;
import com.bwoil.pay.bss.form.ChannelSaveForm;
import com.bwoil.pay.common.entity.PayChannel;

public interface ChannelService {

	PaginationResult<PayChannel> list(ChannelQueryForm form);

	void save(ChannelSaveForm form);

	QueryResult<PayChannel> list();

}
