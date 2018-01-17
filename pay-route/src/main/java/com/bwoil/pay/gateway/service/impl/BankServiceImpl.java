package com.bwoil.pay.gateway.service.impl;

import com.bwoil.pay.common.dao.BankDao;
import com.bwoil.pay.common.util.BeanUtils;
import com.bwoil.pay.gateway.form.bank.BankListForm;
import com.bwoil.pay.gateway.result.bank.BankInfo;
import com.bwoil.pay.gateway.service.BankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankDao bankDao;
    @Override
    public List<BankInfo> list(BankListForm form){

        String appid=form.getAppid();


        StringBuilder sql=new StringBuilder("SELECT b.bank_code as bankCode,b.bank_name as bankName,b.logo,max(cb.max) From pay_channel_bank cb" );
                sql.append(" left JOIN pay_bank b on(cb.bank_code=b.bank_code)" );
                sql.append(" left JOIN pay_channel_config config on(config.channel_code=cb.channel_code)" );
                sql.append(" left JOIN pay_app app on(app.appid=config.appid)") ;
                sql.append( " where cb.`status`=1 and b.`status`=1 AND config.enbable=1 and config.`status`=1 and app.`status`=1 " );
                sql.append( " and cb.max>=:amount and app.appid=:appid");
                sql.append(" GROUP BY b.bank_code ORDER BY b.sort");

         Map<String,Object> param=new HashMap<>(2);
         if(StringUtils.isBlank(form.getAmount())){
             param.put("amount",0);
         }else{
             param.put("amount",form.getAmount());
         }
         param.put("appid",appid);
         List<Map<String, Object>> result= bankDao.queryByNativeSQL(sql.toString(),param);

         if(result==null||result.isEmpty()){
             return Collections.EMPTY_LIST;
         }
        List<BankInfo> bankInfos=new ArrayList<>(result.size());
        result.forEach(r->{
            BankInfo bankInfo=new BankInfo();
            BeanUtils.copy(bankInfo,r);
            bankInfos.add(bankInfo);
        });
         return bankInfos;

    }
}
