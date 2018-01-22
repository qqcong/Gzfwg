package com.bbbbb.pay.gateway.result.bank;

import com.bbbbb.pay.gateway.result.SignResult;
import lombok.Data;

/**
 * 
 * 卡信息
 */
@Data
public class CardInfo extends SignResult{
   private String bankCode;
   private String bankName;
   private String cardType;
   private String valid;
}
