package com.bwoil.pay.gateway.service.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bwoil.pay.common.constants.Channel;
import com.bwoil.pay.common.constants.TradeStatus;
import com.bwoil.pay.common.dao.AppDao;
import com.bwoil.pay.common.dao.BankCardDao;
import com.bwoil.pay.common.dao.BankDao;
import com.bwoil.pay.common.dao.BindOrderDao;
import com.bwoil.pay.common.dao.BindRecordDao;
import com.bwoil.pay.common.dao.ChannelConfigDao;
import com.bwoil.pay.common.dao.OpenUserDao;
import com.bwoil.pay.common.dao.TransactionDao;
import com.bwoil.pay.common.entity.App;
import com.bwoil.pay.common.entity.Bank;
import com.bwoil.pay.common.entity.BankCard;
import com.bwoil.pay.common.entity.BindOrder;
import com.bwoil.pay.common.entity.BindRecord;
import com.bwoil.pay.common.entity.ChannelConfig;
import com.bwoil.pay.common.entity.OpenUser;
import com.bwoil.pay.common.entity.Transaction;
import com.bwoil.pay.common.pay.FastPay;
import com.bwoil.pay.common.pay.form.PayForm;
import com.bwoil.pay.common.pay.result.PayResult;
import com.bwoil.pay.common.util.ErrorsUtils;
import com.bwoil.pay.common.util.SerialNumberUtils;
import com.bwoil.pay.gateway.form.bank.CardForm;
import com.bwoil.pay.gateway.form.bind.BindSubmitForm;
import com.bwoil.pay.gateway.form.bind.UnbindForm;
import com.bwoil.pay.gateway.form.pay.FirstPayForm;
import com.bwoil.pay.gateway.form.pay.PayComfirmForm;
import com.bwoil.pay.gateway.form.pay.PaySubmitForm;
import com.bwoil.pay.gateway.form.pay.QueryPayForm;
import com.bwoil.pay.gateway.result.bank.CardInfo;
import com.bwoil.pay.gateway.result.bind.BindComfirmResult;
import com.bwoil.pay.gateway.result.pay.PayOrderInfo;
import com.bwoil.pay.gateway.result.pay.PaySubmitResult;
import com.bwoil.pay.gateway.service.BindService;
import com.bwoil.pay.gateway.service.CardService;
import com.bwoil.pay.gateway.service.PayInfoNotify;
import com.bwoil.pay.gateway.service.PayService;

import lombok.extern.apachecommons.CommonsLog;

/**
 * @author chendx
 * 支付服务实现
 */
@Service
@CommonsLog
public class PayServiceImpl implements PayService {

    @Autowired
    private ChannelConfigDao channelConfigDao;

    @Autowired
    private CardService cardService;
    @Autowired
    private BindRecordDao bindRecordDao;

    @Autowired
    private OpenUserDao openUserDao;
    @Autowired
    private BankCardDao bankCardDao;

    @Autowired
    private PayInfoNotify notify;
    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private BindService bindService;
    
    @Autowired
    private AppDao appDao;
    
    @Autowired
    private BankDao bankDao;
    
    @Autowired
    private BindOrderDao bindOrderDao;


    private ChannelConfig findBestPayChannel(PaySubmitForm form) {

        ChannelConfig channelConfig;
        if(StringUtils.isNotBlank(form.getChannel())){
            Map<Object,Object> map=new HashMap<>(2);
            map.put("app.appid",form.getAppid());
            map.put("payChannel.code",form.getChannel());
            channelConfig= channelConfigDao.findOneByMap(map);
            if(channelConfig==null){
                throw ErrorsUtils.buildSystemException("BW00011");
            }
            if(channelConfig.getApp().getStatus()!=1){
                throw ErrorsUtils.buildSystemException("BW00012");
            }
            if(!channelConfig.isEnable()||channelConfig.getPayChannel().getStatus()!=1){
                throw ErrorsUtils.buildSystemException("BW00013");
            }
            return channelConfig;
        }else{

            Map<Object,Object> map=new HashMap<>(4);
            map.put("app.appid",form.getAppid());
            map.put("enable",true);
            map.put("app.status",1);
            map.put("payChannel.status",1);
            /**
             *获取可用的支付渠道
             */
            List<ChannelConfig> channelConfigs= channelConfigDao.findByMap(map);
            //TODO 去除不支持银行

            //TODO 去除限额的银行
            if(channelConfigs==null||channelConfigs.isEmpty()){
                throw ErrorsUtils.buildSystemException("BW00011");
            }


            /**
             * 查找费用最少支付渠道
             * 对支付费用进行从小到大进行排序
             */
            channelConfigs.sort(new  Comparator<ChannelConfig>(){
                Integer amount=  Integer.parseInt(form.getAmount());
                @Override
                public int compare(ChannelConfig o1, ChannelConfig o2) {
                    return o1.calculatePayFee(amount).compareTo(o2.calculatePayFee(amount));
                }
            });

            return channelConfigs.get(0);

        }

    }

    private ChannelConfig findBestPayChannel(FirstPayForm form){
        PaySubmitForm submitForm=new PaySubmitForm();
        BeanUtils.copyProperties(form,submitForm);
        return  findBestPayChannel(submitForm);
    }
    private FastPay buildPay(ChannelConfig channelConfig){
        String payClass=channelConfig.getPayChannel().getPayClass();
        try {
            Class<?> clazz = Class.forName(payClass);
            FastPay pay=(FastPay) clazz.getConstructor().newInstance();
            pay.setConfig(channelConfig.getConfig());
            return pay;
        }catch (Exception e){
            log.error("支付渠道设置错误",e);
            throw ErrorsUtils.buildSystemException("BW00014");
        }
    }

    @Transactional
    @Override
    public PaySubmitResult submit(PaySubmitForm form) {
        //检查

        Map<Object,Object> map=new HashMap<>(4);
        map.put("appid",form.getAppid());
        map.put("orderNo",form.getOrderNo());
        map.put("tradeStatus", TradeStatus.SUCCESS);
        List<Transaction> orders= transactionDao.findByMap(map);
        if(orders!=null&&orders.size()>0){
            throw ErrorsUtils.buildSystemException("BW00114");
        }
        /**
         *获取最优的支付通道
         */
        ChannelConfig config= findBestPayChannel(form);
        //获取绑卡信息

        map=new HashMap<>(4);
        if(StringUtils.isNotBlank(form.getCardNo())) {
            map.put("cardNo", form.getCardNo());
        }
        if(StringUtils.isNotBlank(form.getBindId())){
            map.put("bindId", form.getBindId());
        }
        map.put("status", 1);

        BankCard bankCard=bankCardDao.findOneByMap(map);
        if(bankCard==null){
            throw ErrorsUtils.buildSystemException("BW00015");
        }

        map=new HashMap<>(4);
        map.put("card", bankCard);
        map.put("channelConfig", config);
        map.put("status", 1);
        map.put("openUser.appUserId",form.getUserId());



        /**
         * 查找对应绑卡信息
         */
        BindRecord bindRecord=bindRecordDao.findOneByMap(map,"createTime desc");

        /**
         * 些渠道对应的绑卡渠道不存在
         * 直接调用直接绑卡接口
         */
        String bindId=null;
        if(bindRecord==null){
            BindSubmitForm submitForm=new BindSubmitForm();
            submitForm.setUserName(bankCard.getUserName());
            submitForm.setUserId(form.getUserId());
            submitForm.setAppid(form.getAppid());
            submitForm.setMobile(bankCard.getMobile());
            submitForm.setChannel(form.getChannel());
            submitForm.setIdCardNo(bankCard.getIdCardNo());
            submitForm.setCardNo(bankCard.getCardNo());

            submitForm.setBankCode(bankCard.getBank().getBankCode());
            submitForm.setRequestNo(SerialNumberUtils.generate());
            BindComfirmResult bindResult= bindService.directBind(submitForm);



            if("SUCCESS".equals(bindResult.getStatus())){
                bindId=bindResult.getBindId();
                //重新获取绑卡信息
                //bindRecord=bindRecordDao.findOneByMap(map);

            }else{
                //如果绑卡失败，则取原来绑卡的渠道
                map=new HashMap<>(3);
                map.put("card", bankCard);
                map.put("status", 1);
                map.put("openUser.appUserId",form.getUserId());
                bindRecord=bindRecordDao.findOneByMap(map, "createTime desc");
                bindId=bindRecord.getBindId();

            }
        }else {
            bindId=bindRecord.getBindId();
        }
       // config=  bindRecord.getChannelConfig();
        FastPay pay=buildPay(config);
        PayForm payForm=new PayForm();
        payForm.setAccount(bindId);
        payForm.setAmount(Integer.valueOf(form.getAmount()));
        payForm.setBody(form.getProductName());
        payForm.setSubject(form.getProductName());
        payForm.setChannel(Channel.valueOf(config.getPayChannel().getCode()));
        payForm.setIp(form.getIp());
        payForm.setOrderNo(form.getOrderNo());
        payForm.setIdentityId(form.getUserId());
        PayResult result= pay.pay(payForm);


        Transaction t=new Transaction();
        t.setTransId(SerialNumberUtils.generate());
        t.setAmount(Integer.valueOf(Integer.valueOf(form.getAmount())));
        t.setNotified(false);
        t.setAppid(form.getAppid());
        t.setBody(form.getProductName());
        t.setCardId(bankCard.getId());
        t.setChannel(config);
        t.setClientIp(form.getIp());
        t.setCreated(new Date());
        t.setOrderNo(form.getOrderNo());
        t.setRequestNo(form.getRequestNo());
        t.setCallbackUrl(form.getCallbackUrl());
        t.setSubject(form.getProductName());
        map=new HashMap<>(4);
        map.put("user", bankCard.getUser());
        map.put("app.appid", form.getAppid());
        OpenUser openUser=openUserDao.findOneByMap(map);
        t.setOpenid(openUser.getOpenid());
        t.setTradeStatus(TradeStatus.WAIT);

        transactionDao.save(t);

        PaySubmitResult submitResult=new PaySubmitResult();
        //提交支付成功
        if(StringUtils.isBlank(result.getErrCode())){
            submitResult.setAmount(result.getPayAmount().toString());
            submitResult.setStatus("SUCCESS");
            t.setTransactionNo(result.getTransactionNo());
            t.setPayAmount(result.getPayAmount());
            submitResult.setTransId(t.getTransId());

        }else{
            submitResult.setAmount(result.getPayAmount().toString());
            submitResult.setStatus("FAIL");
            submitResult.setTransId(t.getTransId());
            submitResult.setCode(result.getErrCode());
            submitResult.setMsg(result.getErrMsg());
            t.setFailureCode(result.getErrCode());
            t.setFailureMsg(result.getErrMsg());
            t.setNotified(true);
            t.setTradeStatus(TradeStatus.ERROR);
        }
        transactionDao.save(t);
        return submitResult;
    }

    @Transactional
    @Override
    public PayOrderInfo comfirm(PayComfirmForm form) {



        Transaction t=transactionDao.findById(form.getTransId());
        if(t==null){
            throw ErrorsUtils.buildSystemException("BW00016");
        }
        if(t.isPaid()){
            throw ErrorsUtils.buildSystemException("BW00114");
        }
        FastPay pay=buildPay(t.getChannel());
        PayResult result=pay.comfirm(t.getTransactionNo(),form.getValidateCode());
        if(result.isPaid()){
            t.setPaid(true);
            t.setTimePaid(result.getTimePaid());
            t.setTradeStatus(TradeStatus.SUCCESS);
           if( result.getPayAmount()!=null){
               t.setPayAmount(result.getPayAmount());
           }
        }else{
            t.setTradeStatus(result.getTradeStatus());
            t.setFailureMsg(result.getErrMsg());
            t.setFailureCode(result.getErrCode());
        }
        transactionDao.save(t);
        notify.notify(t);
        Map<Object,Object> map=new HashMap<>(4);

        map.put("cardNo", t.getCardId());

        map.put("status", 1);

        BankCard bankCard=bankCardDao.findById(t.getCardId());

        bankCard.setUsedCount(bankCard.getUsedCount()+1);
        bankCard.setLastUsed(new Date());
        bankCardDao.save(bankCard);

        PayOrderInfo info=new PayOrderInfo();
        info.setAmount(t.getAmount().toString());
        info.setBindId(bankCard.getBindId());
        info.setChannel(t.getChannel().getPayChannel().getCode());
        info.setOrderNo(t.getOrderNo());
        info.setStatus(t.getTradeStatus().name());
        info.setTransId(t.getTransId());
        info.setCode(t.getFailureCode());
        info.setMsg(t.getFailureMsg());

        return info;
    }

    @Override
    @Transactional
    public PayOrderInfo query(QueryPayForm form) {
        Transaction t=null;
        if(StringUtils.isNotBlank(form.getTransId())) {
            t = transactionDao.findById(form.getTransId());
        }
        if(StringUtils.isNotBlank(form.getRequestNo())) {
            t = transactionDao.findOneByProperty("requestNo",form.getRequestNo());
        }
        if(t==null){
            throw ErrorsUtils.buildSystemException("BW00016");
        }
        FastPay pay=buildPay(t.getChannel());
        PayResult payResult= pay.query(t.getOrderNo(),t.getTransactionNo());

        t.setFailureCode(payResult.getErrCode());
        t.setFailureMsg(payResult.getErrMsg());
        if(payResult.getTradeStatus()!=null) {
            t.setTradeStatus(payResult.getTradeStatus());
        }
        t.setPaid(payResult.getTradeStatus()==TradeStatus.SUCCESS);
        t.setPayAmount(payResult.getPayAmount());
        transactionDao.save(t);
        BankCard bankCard=bankCardDao.findById(t.getCardId());
        PayOrderInfo info=new PayOrderInfo();
        info.setAmount(t.getAmount().toString());
        info.setBindId(bankCard.getBindId());
        info.setChannel(t.getChannel().getPayChannel().getName());
        info.setOrderNo(t.getOrderNo());
        info.setStatus(t.getTradeStatus().name());
        info.setTransId(t.getTransId());
        info.setMsg(t.getFailureMsg());
        return info;
    }

    @Transactional
    @Override
    public PaySubmitResult firstPay(FirstPayForm form) {

        Map<Object,Object> map=new HashMap<>(4);
        map.put("appid",form.getAppid());
        map.put("orderNo",form.getOrderNo());
        map.put("tradeStatus", TradeStatus.SUCCESS);
        List<Transaction> orders= transactionDao.findByMap(map);
        if(orders!=null&&orders.size()>0){
            throw ErrorsUtils.buildSystemException("BW00114");
        }
        /**
         *获取最优的支付通道
         */

        //如果没有传bankCode
        if(StringUtils.isBlank(form.getBankCode())){
            CardForm cardForm=new CardForm();
            cardForm.setCardNo(form.getCardNo());
            CardInfo cardInfo=cardService.getCardInfo(cardForm);
            if(cardInfo==null||StringUtils.isBlank(cardInfo.getBankCode())){
                throw ErrorsUtils.buildSystemException("BW00017");
            }
            form.setBankCode(cardInfo.getBankCode());
        }             
        
        
        /**  获取最优的支付通道 */
        ChannelConfig config= findBestPayChannel(form);
        FastPay pay=buildPay(config);              
        
        PaySubmitResult payResult = new PaySubmitResult();
        if(pay.isSupportFirstPay()) {
        	payResult = yeepayFirstPay(pay, form, config);
        } else {
        	payResult = baofuFirstPay(form);
        }
        
        return payResult;        
    }
    
    public PaySubmitResult yeepayFirstPay(FastPay pay, FirstPayForm form, ChannelConfig config) {
    	//1.YEEPAY查询卡信息
    	CardForm cardForm=new CardForm();
        cardForm.setCardNo(form.getCardNo());
        CardInfo cardInfo=cardService.getCardInfo(cardForm);
        if(cardInfo==null||StringUtils.isBlank(cardInfo.getBankCode())){
            throw ErrorsUtils.buildSystemException("BW00017");
        }       
    	
    	PaySubmitResult payResult = new PaySubmitResult();
    	com.bwoil.pay.common.pay.form.FirstPayForm commonForm = parseFirstPayForm(form);
    	PayResult result = pay.firstPay(commonForm);
    	
    	//首次支付成功，执行 2.写bankCard 3.写bindRrcord 4.写bindOrder
    	BankCard bankCard= new BankCard();
    	if(result.isPaid()) {
	    	log.info("添加绑卡订单记录信息");
	    	App app= appDao.findById(form.getAppid());
	    	Bank bank=bankDao.findById(form.getBankCode());
	    	BindOrder bindOrder=new BindOrder();
	    	bindOrder.setApp(app);
	        bindOrder.setBank(bank);
	        bindOrder.setCardNo(form.getCardNo());
	        bindOrder.setChannel(config);
	        bindOrder.setCreateTime(new Date());
	        bindOrder.setName(form.getUserName());
	        bindOrder.setPhone(form.getMobile());
	        bindOrder.setRequestId(form.getRequestNo());
	        bindOrder.setStatus(0);
	        bindOrder.setUserId(form.getUserId());
	        bindOrder.setIdCardNo(form.getIdCardNo()); 
	        bindOrder.setBindId(result.getBindId());
	        bindOrderDao.save(bindOrder);
	        bankCard= bindService.wirteSuccessBindResult(bindOrder);
    	}
        
        //5.写transaction       
    	Map<Object,Object> map=new HashMap<>(4);
        map.put("appUserId", form.getUserId());
        map.put("app.appid", form.getAppid());
        OpenUser openUser=openUserDao.findOneByMap(map);
        Transaction t=new Transaction();
        t.setTransId(SerialNumberUtils.generate());
        t.setAmount(Integer.valueOf(Integer.valueOf(form.getAmount())));
        t.setNotified(false);
        t.setAppid(form.getAppid());
        t.setBody(form.getProductName());
        if(bankCard.getId() != null) {
        	t.setCardId(bankCard.getId());
        }
        t.setChannel(config);
        t.setClientIp(form.getIp());
        t.setCreated(new Date());
        t.setOrderNo(form.getOrderNo());
        t.setRequestNo(form.getRequestNo());
        t.setCallbackUrl(form.getCallbackUrl());
        t.setSubject(form.getProductName());
        t.setFailureCode(result.getErrCode());
        t.setFailureMsg(result.getErrMsg());        
        t.setOpenid(openUser.getOpenid());
        transactionDao.save(t);
        
    	payResult.setAmount(form.getAmount());
//    	payResult.setStatus(result.getStatus());
//    	payResult.setTransId(result.getTransactionNo());
//    	payResult.setMsg(result.getMsg());
//    	payResult.setCode(result.getErrCode());
    	
    	return payResult;
    }
    
    public PaySubmitResult baofuFirstPay(FirstPayForm form) {
    	BindSubmitForm bindform=new BindSubmitForm();
        bindform.setRequestNo(form.getRequestNo());

        bindform.setCardNo(form.getCardNo());
        bindform.setIdCardNo(form.getIdCardNo());
        bindform.setUserName(form.getUserName());
        bindform.setMobile(form.getMobile());
        bindform.setBankCode(form.getBankCode());
        bindform.setUserId(form.getUserId());
        bindform.setAppid(form.getAppid());
        bindform.setChannel(form.getChannel());
        BindComfirmResult bindResult= bindService.directBind(bindform);

        //绑卡成功或已绑卡
        if("SUCCESS".equals(bindResult.getStatus())||"BW00019".equals(bindResult.getCode())) {
            PaySubmitForm payForm = new PaySubmitForm();
            payForm.setCallbackUrl(form.getCallbackUrl());
            payForm.setAmount(form.getAmount());
            payForm.setProductName(form.getProductName());
            payForm.setIp(form.getIp());
            payForm.setOrderNo(form.getOrderNo());
            payForm.setCardNo(form.getCardNo());
            payForm.setRequestNo(form.getRequestNo());
            payForm.setUserId(form.getUserId());
            payForm.setAppid(form.getAppid());
            PaySubmitResult payResult = submit(payForm);

            if(!"SUCCESS".equals(payResult.getStatus())){
                UnbindForm unbindForm=new UnbindForm();
                unbindForm.setCardNo(form.getCardNo());
                unbindForm.setUserId(form.getUserId());
                bindService.unbind(unbindForm);
            }

            return payResult;
        }else{
            PaySubmitResult payResult = new PaySubmitResult();
            payResult.setAmount(form.getAmount());
            payResult.setStatus("FAIL");
            payResult.setTransId(form.getRequestNo());
            payResult.setMsg(bindResult.getMsg());
            payResult.setCode(bindResult.getMsg());
            return payResult;
        }
    }
    
    public com.bwoil.pay.common.pay.form.FirstPayForm parseFirstPayForm(FirstPayForm form) {
    	com.bwoil.pay.common.pay.form.FirstPayForm commonForm = new  com.bwoil.pay.common.pay.form.FirstPayForm();
    	commonForm.setAmount(form.getAmount());
    	commonForm.setAppid(form.getAppid());
    	commonForm.setBankCode(form.getBankCode());
    	commonForm.setCallbackUrl(form.getCallbackUrl());
    	commonForm.setCardNo(form.getCardNo());
    	commonForm.setChannel(form.getChannel());
    	commonForm.setIdCardNo(form.getIdCardNo());
    	commonForm.setIp(form.getIp());
    	commonForm.setMobile(form.getMobile());
    	commonForm.setOrderNo(form.getOrderNo());
    	commonForm.setProductName(form.getProductName());
    	commonForm.setRequestNo(form.getRequestNo());
    	commonForm.setUserId(form.getUserId());
    	commonForm.setUserName(form.getUserName());
    	
    	return commonForm;
    }
    
}
