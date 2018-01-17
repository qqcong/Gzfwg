package com.bwoil.pay.gateway.form.bind;

import com.bwoil.common.framework.validation.*;
import com.bwoil.pay.gateway.form.SignForm;
import lombok.Data;

/**
 * @author chendx
 * 绑卡表单
 */
@Data
public class BindSubmitForm extends SignForm{

    @NotBlank
    private String  userId;//	是	string	用户编号
    @NotBlank
    private String  requestNo;//	是	string	请求编码，请确保唯一
    private String  channel;//	否	string	绑卡渠道 默认：”ALL” 全部 可选 “YEEPAY”，”NEWPAY”
    @NotBlank
    @Mobile
    private String  mobile;//	是	string	手机号
    private String  bankCode;// 否  银行简码 如 ICBC
    @NotBlank
    @BankCard
    private String  cardNo;//	是	string	卡号
    @NotBlank
    //@IdCard
    private String  idCardNo;//	是	string	身份证号
    @NotBlank
    @Chinese
    private String  userName;//	是	string	用户姓名
}
