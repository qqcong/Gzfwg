package com.bwoil.pay.common.pay.result;

import lombok.Data;

/**
 * @author chendx
 * 查询卡信息接口
 */
@Data
public class CardInfoResult {
    private String bankCode;
    private String bankName;
    private String cardType;
    private String valid;

}
