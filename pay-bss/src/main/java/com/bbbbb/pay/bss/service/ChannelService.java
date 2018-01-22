package com.bbbbb.pay.bss.service;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.common.framework.result.QueryResult;
import com.bbbbb.pay.bss.form.ChannelQueryForm;
import com.bbbbb.pay.bss.form.ChannelSaveForm;
import com.bbbbb.pay.common.entity.PayChannel;

public interface ChannelService {

	PaginationResult<PayChannel> list(ChannelQueryForm form);

	void save(ChannelSaveForm form);

	QueryResult<PayChannel> list();

}
