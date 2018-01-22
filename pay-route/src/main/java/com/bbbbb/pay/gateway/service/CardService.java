package com.bbbbb.pay.gateway.service;


import com.bbbbb.pay.gateway.form.bank.CardForm;
import com.bbbbb.pay.gateway.result.bank.CardInfo;

public interface CardService {
    CardInfo getCardInfo(CardForm form);
}
