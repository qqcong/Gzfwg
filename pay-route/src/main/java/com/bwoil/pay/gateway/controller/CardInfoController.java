package com.bwoil.pay.gateway.controller;

import com.bwoil.pay.gateway.form.bank.CardForm;
import com.bwoil.pay.gateway.result.SignResult;
import com.bwoil.pay.gateway.result.bank.CardInfo;
import com.bwoil.pay.gateway.service.CardService;
import com.bwoil.pay.gateway.util.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/card")
public class CardInfoController extends BaseController {

      @Autowired
      private CardService cardService;
      @RequestMapping(value = "info",method = RequestMethod.POST)
      public SignResult info(@Valid @RequestBody CardForm form){
          SignResult errResult = checkFormSign(form);
          if (errResult != null) {
              SignUtils.sign(errResult);
              return errResult;
          }
          CardInfo cardInfo=  cardService.getCardInfo(form);
          cardInfo.setAppid(form.getAppid());
          SignUtils.sign(cardInfo);
          return cardInfo;
      }
}
