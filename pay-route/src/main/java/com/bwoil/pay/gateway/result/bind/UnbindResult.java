package com.bwoil.pay.gateway.result.bind;

import com.bwoil.pay.gateway.result.SignResult;
import lombok.Data;

/**
 * @author chendx
 * 解绑结果
 */
@Data
public class UnbindResult extends SignResult {

   private String status;//	string	解绑状态 SUCCESS：成功 FAIL：失败
}
