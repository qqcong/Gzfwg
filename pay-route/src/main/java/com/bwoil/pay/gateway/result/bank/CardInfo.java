package com.bwoil.pay.gateway.result.bank;

import com.bwoil.pay.gateway.result.SignResult;
import lombok.Data;

/**
 * @author chendx
 * 卡信息
 */
@Data
public class CardInfo extends SignResult{
   private String bankCode;
   private String bankName;
   private String cardType;
   private String valid;
}
