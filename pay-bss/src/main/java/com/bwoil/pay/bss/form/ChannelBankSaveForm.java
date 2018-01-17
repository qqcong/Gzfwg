package com.bwoil.pay.bss.form;

import lombok.Data;

@Data
public class ChannelBankSaveForm {

	private Long id;

	private String channelCode;

	private String bankCode;

	private Integer min;

	private Integer max;

	private Integer status;

}
