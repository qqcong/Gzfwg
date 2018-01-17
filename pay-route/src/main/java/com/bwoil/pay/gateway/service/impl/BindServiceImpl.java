package com.bwoil.pay.gateway.service.impl;

import com.bwoil.common.framework.util.JsonUtils;
import com.bwoil.pay.common.dao.*;
import com.bwoil.pay.common.entity.*;
import com.bwoil.pay.common.pay.BindCard;
import com.bwoil.pay.common.pay.form.UnBindInterForm;
import com.bwoil.pay.common.pay.result.BindResult;
import com.bwoil.pay.common.util.ErrorsUtils;
import com.bwoil.pay.common.util.SerialNumberUtils;
import com.bwoil.pay.gateway.form.bank.CardForm;
import com.bwoil.pay.gateway.form.bind.*;
import com.bwoil.pay.gateway.result.bank.CardInfo;
import com.bwoil.pay.gateway.result.bind.BindCardInfo;
import com.bwoil.pay.gateway.result.bind.BindComfirmResult;
import com.bwoil.pay.gateway.result.bind.BindSubmitResult;
import com.bwoil.pay.gateway.result.bind.UnbindResult;

import com.bwoil.pay.gateway.service.BindService;
import com.bwoil.pay.gateway.service.CardService;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author chendx
 *绑卡操作服务
 */
@Service
@CommonsLog
public class BindServiceImpl implements BindService {

    @Autowired
    private ChannelConfigDao channelConfigDao;

    @Autowired
    private CardService cardService;
    @Autowired
    private BindRecordDao bindRecordDao;
    @Autowired
    private BindOrderDao bindOrderDao;
    @Autowired
    private OpenUserDao openUserDao;
    @Autowired
    private BankCardDao bankCardDao;
    @Autowired
    private AppDao appDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private BankDao bankDao;

    private ChannelConfig findBestBindChannel(BindSubmitForm form) {

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
             * 查找费用最少绑卡渠道
             */
            List<ChannelConfig> channelConfigs= channelConfigDao.findByMap(map,"authFee");

            if(channelConfigs==null||channelConfigs.isEmpty()){
                throw ErrorsUtils.buildSystemException("BW00011");
            }
            ChannelConfig config=channelConfigs.get(0);
            log.info("已获取最优绑卡渠道:"+config.getId()+"-->"+config.getPayChannel().getName());
            return config;

        }

    }


    private BindCard buildBindCard(ChannelConfig channelConfig){
        String bindClass=channelConfig.getPayChannel().getBindClass();
        try {
            Class<?> clazz = Class.forName(bindClass);
            BindCard bindCard=(BindCard) clazz.getConstructor().newInstance();
            bindCard.setConfig(channelConfig.getConfig());
            log.info("成功获取绑卡实现类["+bindClass+"]");
            return bindCard;
        }catch (Exception e){
            log.error("绑卡渠道设置错误",e);
            throw ErrorsUtils.buildSystemException("BW00014");
        }
    }
    @Override
    @Transactional
    public BindSubmitResult submit(BindSubmitForm form) {

        BindSubmitResult result=new BindSubmitResult();
        log.info("查看绑卡记录");
        Map<Object,Object> map=new HashMap<>(3);
        map.put("openUser.appUserId",form.getUserId());
        map.put("card.cardNo",form.getCardNo());
        map.put("status",1);
        BindRecord record=bindRecordDao.findOneByMap(map);
        //已经绑卡，无需重复
        if(record!=null){
             result=new BindSubmitResult();
             result.setCode("BW00019");
             result.setMsg(ErrorsUtils.getMessage("BW00019"));
             log.info("已经绑卡，无需重复操作");
             return result;
        }

        map=new HashMap<>(3);
        map.put("requestId",form.getRequestNo());
        map.put("app.appid",form.getAppid());
        map.put("status",1);
        BindOrder bindOrder= bindOrderDao.findOneByMap(map);

        if(bindOrder!=null&&bindOrder.getStatus()==2){
            throw ErrorsUtils.buildSystemException("BW00019");
        }
        //如果没有传bankCode
      //  if(StringUtils.isBlank(form.getBankCode())){
            CardForm cardForm=new CardForm();
            cardForm.setCardNo(form.getCardNo());
            CardInfo cardInfo=cardService.getCardInfo(cardForm);
            if(cardInfo==null||StringUtils.isBlank(cardInfo.getBankCode())){
                throw ErrorsUtils.buildSystemException("BW00017");
            }
            form.setBankCode(cardInfo.getBankCode());
      //  }
        //获取绑卡渠道
        ChannelConfig channelConfig = findBestBindChannel (form);
        //数据库没配置数据，直接赋值
        if(channelConfig.getConfig() == null || channelConfig.getConfig().size() == 0) {       	        
	        Map<String, String> config = new HashMap<String, String>();
	        config.put("merchantno","10000449592");
	        config.put("merchantPrivateKey","MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIVbHgGK4dLuoQkTvdEJ4ozAKxhgfoyAOJ72PG5r1/fmy83CD4h6z5suNi+KR7EcCZmbgqxm1o6K9bzywZpnrlaX9geY74vElsQjpkCMsKDAUPFagid4/tC4bxeFfOAmzgQdGKVHdn+4pBqvG2EdXXaaK/uIxl6rfylcuPVymhPTAgMBAAECgYB1VnwrfUlAcQmu0/kD8r+tevUwaM9Qzw2DCUSZIDkSfrg63pMOUolTkLDK2dBFDVeBGi07hu0o1SpuS+d/9dSWQld0VQMn5wVCxLAiWCs1RL9d9lMszAEDNbdAL+CN68X4Ve89uqRJ/qvOZOkhDZu5kS05CpjsA/pKgv/ce9hHuQJBANXHRvPAqfHSJvMa2qdAYrivWU7fTctWFLbqdhrg7EuGuMcl0WxcKzH2eHIofar8fXeLX39I0TAip0nYl+ML67UCQQCfsaVxWJvW2yQBsPAQPJC6UM5vMJnVhGfSd4e6s8H5PsLeMTZFEC3GQZvTCO0M6qA+scV64NH0x04vMIIpKAZnAkAUXvHm5lQLZBAsGEH/sAX4PVVQS70ZBDHjIEJy2z4JEGRpLbRgFARVtXvSvQMEmRzHmHNYKLMuWA8C3W3Tx94FAkEAl99DvzsM4lYAtVcHx/lBjt/Ao8At3QESF/gzbhz8kcTdYKCewymyzsSgpB/uCYWtplI8xDLBmjBdq8VPVELLawJBALnAOmZ8GUuYH738jp12NH6B3f7o9Wd6l+xdvEJ8JA1/UIQOh2nof2mDR1A7sWTATUmmCAN4y3ThZLTsWv1AYoA=");
	        config.put("yeepayPublicKey","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSp6oYtbBu1E0/tj9pP2zEuXTfgF2vk1wQ7jStNnadm/DZT3TBK8wdQTwMtWILOmeHoSowOPXjNmqmhloE2wwTGWINd1Z2WN2GlU7CgWJL/NGv1iYX3TM5uuN45gYsecoXuVFvzVmYMSdASBjlNQPQzbK0PUc+8zsOl6gK1LU33wIDAQAB");
	        channelConfig.setConfig(config);
        }

        log.info("添加绑卡订单记录信息");
        Bank bank=bankDao.findById(form.getBankCode());
        if(bindOrder==null){
            bindOrder=new BindOrder();
        }
        App app= appDao.findById(form.getAppid());
        bindOrder.setApp(app);
        bindOrder.setBank(bank);
        bindOrder.setCardNo(form.getCardNo());
        bindOrder.setChannel(channelConfig);
        bindOrder.setCreateTime(new Date());
        bindOrder.setName(form.getUserName());
        bindOrder.setPhone(form.getMobile());
        bindOrder.setRequestId(form.getRequestNo());
        bindOrder.setStatus(0);
        bindOrder.setIdCardNo(form.getIdCardNo());
        bindOrder.setUserId(form.getUserId());
        bindOrderDao.save(bindOrder);


        BindCard bindCard=buildBindCard(channelConfig);
        log.info("生成绑卡请求信息");
        com.bwoil.pay.common.pay.form.BindSubmitForm submitForm=new  com.bwoil.pay.common.pay.form.BindSubmitForm();
        submitForm.setRequestNo(form.getRequestNo());

        submitForm.setCardNo(form.getCardNo());
        submitForm.setIdCardNo(form.getIdCardNo());
        submitForm.setName(form.getUserName());
        submitForm.setMobile(form.getMobile());
        submitForm.setBankCode(form.getBankCode());
        //YEEPAY需要传标识Id
        submitForm.setIdentityId(form.getUserId());
        
        log.info("构建绑卡请求:"+ JsonUtils.toString(submitForm));
        BindResult bindResult = bindCard.submit(submitForm);
        //请求成功
        if(StringUtils.isBlank(bindResult.getErrCode())){
            log.info("预绑卡提交成功");
            bindOrder.setStatus(1);
            bindOrderDao.save(bindOrder);
            result.setRequestNo(bindResult.getRequestId());
            result.setOrderNo(bindResult.getRequestId());

        }else{
            log.info("预绑卡提交失败:"+bindResult.getErrMsg());
            bindOrder.setStatus(3);
            bindOrderDao.save(bindOrder);
            //绑卡失败

            if("BW00332".equals(bindResult.getErrCode())){
                result.setMsg("信息不正确，请核对绑卡信息");
            }else {
                result.setMsg(bindResult.getErrMsg());
            }
            result.setCode(bindResult.getErrCode());
        }

        return result;
    }

    @Override
    @Transactional
    public BindComfirmResult comfirm(BindComfirmForm form) {
        Map<Object,Object>  map=new HashMap<>();
        map.put("requestId",form.getRequestNo());
        map.put("app.appid",form.getAppid());
        BindOrder bindOrder= bindOrderDao.findOneByMap(map);

        if(bindOrder==null){
            throw ErrorsUtils.buildSystemException("BW00018");
        }

        map=new HashMap<>(3);
        map.put("openUser.appUserId",bindOrder.getUserId());
        map.put("card.cardNo",bindOrder.getCardNo());
        map.put("status",1);
        BindRecord record=bindRecordDao.findOneByMap(map);
        BindComfirmResult result=new BindComfirmResult();
        //已经绑卡，无需重复
        if(record!=null){
            result.setCode("BW00019");
            result.setMsg(ErrorsUtils.getMessage("BW00019"));
            result.setBindId(record.getCard().getBindId());
            log.info("已经绑卡，无需重复操作");
            return result;
        }

        BindCard bindCard=buildBindCard(bindOrder.getChannel());
        com.bwoil.pay.common.pay.form. BindCardComfirmForm comfirmForm=new com.bwoil.pay.common.pay.form.BindCardComfirmForm();
        comfirmForm.setRequestNo(form.getRequestNo());
        comfirmForm.setValidateCode(form.getValidateCode());
        BindResult bindResult=bindCard.comfirm(comfirmForm);

        if(StringUtils.isBlank(bindResult.getErrCode())){
            log.info("预绑卡提交成功");
            bindOrder.setStatus(2);
            bindOrder.setBindId(bindResult.getBindId());

            bindOrderDao.save(bindOrder);
            BankCard card= wirteSuccessBindResult(bindOrder);
            App app=bindOrder.getApp();
            map=new HashMap<>();
            map.put("app",app);

            map.put("user",card.getUser());
            OpenUser ou= openUserDao.findOneByMap(map);
            result.setBindId(card.getBindId());
            result.setRequestNo(form.getRequestNo());
            result.setBankCode(bindOrder.getBank().getBankCode());
            result.setOpenid(ou.getOpenid());
            result.setStatus("SUCCESS");
        }else{
            log.info("预绑卡提交失败:"+bindResult.getErrMsg());
            bindOrder.setStatus(3);
            bindOrderDao.save(bindOrder);
            if("BW00332".equals(bindResult.getErrCode())){
                result.setMsg("信息不正确，请核对绑卡信息");
            }else {
                result.setMsg(bindResult.getErrMsg());
            }
            result.setStatus("FAIL");
            //绑卡失败
        }
        return result;
    }
    @Override
    @Transactional
    public BindComfirmResult directBind(BindSubmitForm form) {
        BindComfirmResult result=new BindComfirmResult();
        log.info("查看绑卡记录");
        Map<Object,Object> map=new HashMap<>(3);
        map.put("openUser.appUserId",form.getUserId());
        map.put("card.cardNo",form.getCardNo());
        map.put("status",1);
        BindRecord record=bindRecordDao.findOneByMap(map);

        //已经绑卡，无需重复
        if(record!=null){

            if(!form.getMobile().equals(record.getCard().getMobile())){
                result.setBindId(record.getCard().getBindId());
                result.setCode("BW00343");
                result.setMsg(ErrorsUtils.getMessage("BW00343"));
                log.info("手机号不一致,输入["+form.getMobile()+"]实际["+record.getCard().getMobile()+"]");
            }else {

                result.setBindId(record.getCard().getBindId());
                result.setCode("BW00019");
                result.setMsg(ErrorsUtils.getMessage("BW00019"));
                log.info("已经绑卡，无需重复操作");
            }
            return result;
        }

        map=new HashMap<>(2);
        map.put("requestId",form.getRequestNo());
        map.put("app.appid",form.getAppid());
        BindOrder bindOrder= bindOrderDao.findOneByMap(map);

        if(bindOrder!=null&&bindOrder.getStatus()==2){
            throw ErrorsUtils.buildSystemException("BW00019");
        }
        //如果没有传bankCode
        //if(StringUtils.isBlank(form.getBankCode())){
            CardForm cardForm=new CardForm();
            cardForm.setCardNo(form.getCardNo());
            CardInfo cardInfo=cardService.getCardInfo(cardForm);
            if(cardInfo==null||StringUtils.isBlank(cardInfo.getBankCode())){
                throw ErrorsUtils.buildSystemException("BW00017");
            }
            form.setBankCode(cardInfo.getBankCode());
       // }
        //获取绑卡渠道
        ChannelConfig channelConfig = findBestBindChannel (form);

        log.info("添加绑卡订单记录信息");
        Bank bank=bankDao.findById(form.getBankCode());
        if(bindOrder==null){
            bindOrder=new BindOrder();
        }
        App app= appDao.findById(form.getAppid());
        bindOrder.setApp(app);
        bindOrder.setBank(bank);
        bindOrder.setCardNo(form.getCardNo());
        bindOrder.setChannel(channelConfig);
        bindOrder.setCreateTime(new Date());
        bindOrder.setName(form.getUserName());
        bindOrder.setPhone(form.getMobile());
        bindOrder.setRequestId(form.getRequestNo());
        bindOrder.setStatus(0);
        bindOrder.setUserId(form.getUserId());
        bindOrder.setIdCardNo(form.getIdCardNo());

        bindOrderDao.save(bindOrder);


        BindCard bindCard=buildBindCard(channelConfig);
        log.info("生成绑卡请求信息");
        com.bwoil.pay.common.pay.form.BindSubmitForm submitForm=new  com.bwoil.pay.common.pay.form.BindSubmitForm();
        submitForm.setRequestNo(form.getRequestNo());

        submitForm.setCardNo(form.getCardNo());
        submitForm.setIdCardNo(form.getIdCardNo());
        submitForm.setName(form.getUserName());
        submitForm.setMobile(form.getMobile());
        submitForm.setBankCode(form.getBankCode());
        log.info("构建绑卡请求:"+ JsonUtils.toString(submitForm));
        BindResult bindResult = bindCard.directBind(submitForm);
        //请求成功
        if(StringUtils.isBlank(bindResult.getErrCode())){
            log.info("直接绑卡提交成功");
            bindOrder.setStatus(1);
            bindOrder.setBindId(bindResult.getBindId());
            bindOrderDao.save(bindOrder);
            BankCard card= wirteSuccessBindResult(bindOrder);
            result.setBindId(bindResult.getBindId());
            result.setRequestNo(form.getRequestNo());
            result.setStatus("SUCCESS");
        }else{
            log.info("直接绑卡提交失败:"+bindResult.getErrMsg());
            bindOrder.setStatus(3);
            bindOrderDao.save(bindOrder);
            result.setMsg(bindResult.getErrMsg());
            result.setCode(bindResult.getErrCode());
            result.setStatus("FAIL");
            //绑卡失败

        }

        return result;
    }



    /**
     * 记录绑定结果信息
     */
    public BankCard  wirteSuccessBindResult(BindOrder bindOrder){
        Date now=new Date();
        //查看用户是否存在，不存在则创建用户信息
        User user=userDao.findOneByProperty("phone",bindOrder.getPhone());
        if(user==null){
            log.info("用户不存在，正在创建用户信息");
            user=new User();
            user.setIdentityCode(bindOrder.getIdCardNo());
            user.setName(bindOrder.getName());
            user.setPhone(bindOrder.getPhone());
            user.setCreateTime(now);
            userDao.save(user);
        }

        //查看用户与应用关联信息是否存在，不存在则创建
        App app=bindOrder.getApp();
        Map<Object,Object> map=new HashMap<>(2);
        map.put("app",app);
        map.put("appUserId",bindOrder.getUserId());
        map.put("user",user);
        OpenUser ou= openUserDao.findOneByMap(map);
        if(ou==null){
            log.info("应用与用户信息关联不存在，正在创建关联信息");
            ou=new   OpenUser();
            ou.setApp(app);
            ou.setCreateTime(now);
            ou.setAppUserId(bindOrder.getUserId());
            ou.setOpenid(SerialNumberUtils.generate());
            ou.setSubscribed(true);
            ou.setUser(user);
            openUserDao.save(ou);
        }
        map=new HashMap<>(2);
        map.put("cardNo",bindOrder.getCardNo());
        map.put("user",user);
        //查看银行卡与应用关联信息是否存在，不存在则创建
        BankCard card=bankCardDao.findOneByMap(map);
        if(card==null){
            log.info("保存银行卡信息");
            card=new BankCard();
            Bank bank=bindOrder.getBank();
            card.setBank(bank);
            card.setIdCardNo(bindOrder.getIdCardNo());
            card.setCreateTime(now);
            card.setDefaultCard(true);
            card.setLastUsed(now);
            card.setUserName(bindOrder.getName());
            card.setMobile(bindOrder.getPhone());
            card.setStatus(1);
            card.setUsedCount(0);
            card.setCardNo(bindOrder.getCardNo());
            card.setUser(user);
            card.setBindId(SerialNumberUtils.generate());
            bankCardDao.save(card);
        }else{
            card.setStatus(1);
            card.setLastUsed(now);
            bankCardDao.save(card);
        }

        map=new HashMap<>();
        map.put("openUser.appUserId",bindOrder.getUserId());
        map.put("card.cardNo",bindOrder.getCardNo());
        map.put("status",1);
        BindRecord record=bindRecordDao.findOneByMap(map,"createTime desc");
        //查看绑卡记录信息是否存在，不存在则创建
        if(record==null){
            record=new BindRecord();

        }
        record.setCard(card);
        record.setChannelConfig(bindOrder.getChannel());
        record.setStatus(1);
        record.setCreateTime(now);
        record.setOpenUser(ou);
        record.setRequestId(bindOrder.getRequestId());
        record.setBindId(bindOrder.getBindId());
        bindRecordDao.save(record);
        return card;
    }


    @Override
    public BindCardInfo query(QueryBindForm form) {
        Map<Object,Object> map=new HashMap<>();
       // map.put("openUser.appUserId",form.getUserId());
        if(StringUtils.isNotBlank(form.getCardNo())) {
            map.put("cardNo", form.getCardNo());
        }
        if(StringUtils.isNotBlank(form.getBindId())) {
            map.put("bindId", form.getBindId());
        }
        //只查询已绑信息
        map.put("status",1);
        BankCard card=bankCardDao.findOneByMap(map);
        BindCardInfo result=new BindCardInfo();
        if(card!=null&&card.getStatus()==1){
            result.setBankCode(card.getBank().getBankCode());
            result.setBankName(card.getBank().getBankName());
            result.setCardNo(card.getCardNo());
            result.setBindId(card.getBindId());
        }else {
            result.setCode("BW00018");
            result.setMsg("此绑卡信息不存在");
        }
        return result;
    }

    @Override
    @Transactional
    public UnbindResult unbind(UnbindForm form) {
        Map<Object,Object> map=new HashMap<>();
        map.put("openUser.appUserId",form.getUserId());
        if(StringUtils.isNotBlank(form.getCardNo())) {
            map.put("card.cardNo", form.getCardNo());
        }
        if(StringUtils.isNotBlank(form.getBindId())) {
            map.put("bindId", form.getBindId());
        }
        List<BindRecord> records= bindRecordDao.findByMap(map);
        UnbindResult result=new UnbindResult();
        if(records!=null){
            records.forEach(record->{
                ChannelConfig config= record.getChannelConfig();
                BindCard bindCard=buildBindCard(config);
                UnBindInterForm interForm = new UnBindInterForm();
                interForm.setBindId(record.getBindId());
                interForm.setCardNo(record.getCard().getCardNo());
                interForm.setIdentityId(form.getUserId());
                boolean success= bindCard.unBind(interForm);
                if(success){
                    record.setStatus(3);
                    bindRecordDao.save(record);
                    BankCard card=record.getCard();
                    card.setStatus(2);
                    bankCardDao.save(card);
                }
            });
            result.setStatus("SUCCESS");
            return result;
        }else{
            result.setStatus("FAIL");
            return result;
        }

    }


    @Override
    public List<BindCardInfo> list(BindListForm form) {


        Map<Object,Object> map=new HashMap<>(3);
        map.put("openUser.appUserId",form.getUserId());
        map.put("openUser.app.appid",form.getAppid());
        map.put("status",1);
        List<BindRecord> records=bindRecordDao.findByMap(map,"card.lastUsed desc");
        List<BankCard> list=new ArrayList<>();
        //过滤重复银行卡
        records.forEach(bindRecord -> {
           BankCard card= bindRecord.getCard();
             if(!list.contains(card)){
                 list.add(card);
             }
        });
        List<BindCardInfo> result=new ArrayList<>();
        list.forEach(bankCard -> {
            BindCardInfo cardInfo=new BindCardInfo();
            cardInfo.setCardNo(bankCard.getCardNo());
            cardInfo.setBankName(bankCard.getBank().getBankName());
            cardInfo.setBankCode(bankCard.getBank().getBankCode());
            cardInfo.setBindId(bankCard.getBindId());
            cardInfo.setTimestamp(null);
            result.add(cardInfo);
        });
        return result;
    }
}
