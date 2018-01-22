package com.bbbbb.pay.gateway.result.bind;

import com.bbbbb.pay.gateway.result.SignResult;
import lombok.Data;

@Data
public class BindComfirmResult extends SignResult {

    private String requestNo;//	string	请求编号
    private String status;//	string	SUCCESS FAIL ERROR
    private String bindId;//	string	绑卡记录id
    private String bankCode;//  银行简码
    private String openid; //用户在系统的编码

}
