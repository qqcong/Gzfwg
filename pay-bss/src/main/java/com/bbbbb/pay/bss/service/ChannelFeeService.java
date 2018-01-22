package com.bbbbb.pay.bss.service;

import com.bbbbb.common.framework.result.PaginationResult;
import com.bbbbb.pay.bss.form.ChannelFeeQueryForm;
import com.bbbbb.pay.bss.form.ChannelFeeSaveForm;
import com.bbbbb.pay.common.entity.ChannelConfig;

public interface ChannelFeeService {

	PaginationResult<ChannelConfig> list(ChannelFeeQueryForm form);

	void save(ChannelFeeSaveForm form);

}
