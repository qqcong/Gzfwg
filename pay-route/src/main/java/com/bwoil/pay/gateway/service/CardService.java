package com.bwoil.pay.gateway.service;


import com.bwoil.pay.gateway.form.bank.CardForm;
import com.bwoil.pay.gateway.result.bank.CardInfo;

public interface CardService {
    CardInfo getCardInfo(CardForm form);
}
