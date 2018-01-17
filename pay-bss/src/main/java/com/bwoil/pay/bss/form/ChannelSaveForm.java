package com.bwoil.pay.bss.form;

import lombok.Data;

@Data
public class ChannelSaveForm {
	
	private String code;
	
    private String name;

    private boolean enable;

    private Integer status;

    private String bindClass;

    private String payClass;

}
