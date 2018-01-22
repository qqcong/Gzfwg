package com.bbbbb.pay.bss.controller;

import com.bbbbb.pay.bss.form.CardForm;
import com.bbbbb.pay.bss.result.CardInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardInfoController {

      @RequestMapping("info")
      public CardInfo info(@Validated  CardForm form){
          return null;
      }
}
