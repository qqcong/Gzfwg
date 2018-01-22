package com.bbbbb.pay.gateway.result.pay;

import com.bbbbb.pay.gateway.result.SignResult;
import lombok.Data;

@Data
public class PaySubmitResult extends SignResult {

    //参数名	类型	说明
    private String transId;//	string	请求编号
    private String  amount;//	string	请求金额
    private String  status;//	string	请求编号
}
