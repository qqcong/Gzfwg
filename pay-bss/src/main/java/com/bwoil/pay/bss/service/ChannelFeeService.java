package com.bwoil.pay.bss.service;

import com.bwoil.common.framework.result.PaginationResult;
import com.bwoil.pay.bss.form.ChannelFeeQueryForm;
import com.bwoil.pay.bss.form.ChannelFeeSaveForm;
import com.bwoil.pay.common.entity.ChannelConfig;

public interface ChannelFeeService {

	PaginationResult<ChannelConfig> list(ChannelFeeQueryForm form);

	void save(ChannelFeeSaveForm form);

}
