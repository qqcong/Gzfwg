package com.bbbbb.pay.gateway.result;

import lombok.Data;
import lombok.Getter;

@Data

public class SignResult {

    public SignResult(){

    }
    public SignResult(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
    private String appid;
    @Getter
    private String sign;

    private String timestamp=String.valueOf(System.currentTimeMillis());

    private String code="0";

    private String msg;

}
