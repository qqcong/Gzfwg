package com.bbbbb.pay.gateway.result.bind;

import com.bbbbb.pay.gateway.result.SignResult;
import lombok.Data;

@Data
public class BindSubmitResult extends SignResult {

    private String requestNo;//	string	请求编号
    private String  orderNo;//	string	第三方支付系统流水号
    private String  validateCode;//	string	短信验证码，渠道不同 有可能为空


}
