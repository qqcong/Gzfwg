package com.bbbbb.pay.bss.form;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ChannelFeeSaveForm {

	private Long id;
	
	private String appid;
	
	private String channelCode;
	
	private boolean enable;
	
	private BigDecimal authFee;

	private int status;
	
	private BigDecimal payFeeRate;

	private BigDecimal payFeeMin;

	private BigDecimal payFeeMax;

	private String config;

}
