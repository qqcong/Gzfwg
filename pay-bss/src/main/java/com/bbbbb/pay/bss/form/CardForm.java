package com.bbbbb.pay.bss.form;

import com.bbbbb.common.framework.validation.NotBlank;
import com.bbbbb.common.framework.validation.Length;
import lombok.Data;

@Data
public class CardForm {

    @NotBlank
    @Length(min = 6,max = 19)
    private String cardNo;
}
