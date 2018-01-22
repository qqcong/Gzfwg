package com.bbbbb.pay.gateway.result.bind;

import com.bbbbb.pay.gateway.result.SignResult;
import lombok.Data;

/**
 * 绑卡信息
 */
@Data
public class BindCardInfo extends SignResult{

    /**
     * 银行编号
     */
    private String bankCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 绑卡id
     */
    private String bindId;
}
