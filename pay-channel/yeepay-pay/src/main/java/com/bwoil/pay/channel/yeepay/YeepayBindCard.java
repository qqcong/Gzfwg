package com.bwoil.pay.channel.yeepay;


import java.util.HashMap;
import java.util.Map;

import com.bwoil.pay.channel.yeepay.request.BindComfirmRequest;
import com.bwoil.pay.channel.yeepay.request.DirectBindRequest;
import com.bwoil.pay.channel.yeepay.request.PreBindRequest;
import com.bwoil.pay.channel.yeepay.request.QueryBindRequest;
import com.bwoil.pay.channel.yeepay.request.QueryCardRequest;
import com.bwoil.pay.channel.yeepay.request.UnBindRequest;
import com.bwoil.pay.channel.yeepay.response.BaseResponse;
import com.bwoil.pay.channel.yeepay.response.BindResponse;
import com.bwoil.pay.channel.yeepay.response.PreBindResponse;
import com.bwoil.pay.channel.yeepay.response.QueryCarResponse;
import com.bwoil.pay.common.pay.BindCard;
import com.bwoil.pay.common.pay.PayException;
import com.bwoil.pay.common.pay.form.BindCardComfirmForm;
import com.bwoil.pay.common.pay.form.BindSubmitForm;
import com.bwoil.pay.common.pay.form.UnBindInterForm;
import com.bwoil.pay.common.pay.result.BindResult;
import com.bwoil.pay.common.pay.result.CardInfoResult;

import lombok.extern.apachecommons.CommonsLog;

/**
 * @author chendx
 * 易宝绑卡实现
 */
@CommonsLog
public class YeepayBindCard implements BindCard {

    private Map<String,String> config=new HashMap<>();

    @Override
    public void setConfig(Map<String, String> config) {
        this.config=config;
    }
    @Override
    public CardInfoResult cardInfo(String cardNo) {

        /**
         * 官方测试商户密钥，仅用于查询卡信息 不存在安全问题
         */
        config.put("merchantno","10000449592");

        config.put("merchantPrivateKey","MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIVbHgGK4dLuoQkTvdEJ4ozAKxhgfoyAOJ72PG5r1/fmy83CD4h6z5suNi+KR7EcCZmbgqxm1o6K9bzywZpnrlaX9geY74vElsQjpkCMsKDAUPFagid4/tC4bxeFfOAmzgQdGKVHdn+4pBqvG2EdXXaaK/uIxl6rfylcuPVymhPTAgMBAAECgYB1VnwrfUlAcQmu0/kD8r+tevUwaM9Qzw2DCUSZIDkSfrg63pMOUolTkLDK2dBFDVeBGi07hu0o1SpuS+d/9dSWQld0VQMn5wVCxLAiWCs1RL9d9lMszAEDNbdAL+CN68X4Ve89uqRJ/qvOZOkhDZu5kS05CpjsA/pKgv/ce9hHuQJBANXHRvPAqfHSJvMa2qdAYrivWU7fTctWFLbqdhrg7EuGuMcl0WxcKzH2eHIofar8fXeLX39I0TAip0nYl+ML67UCQQCfsaVxWJvW2yQBsPAQPJC6UM5vMJnVhGfSd4e6s8H5PsLeMTZFEC3GQZvTCO0M6qA+scV64NH0x04vMIIpKAZnAkAUXvHm5lQLZBAsGEH/sAX4PVVQS70ZBDHjIEJy2z4JEGRpLbRgFARVtXvSvQMEmRzHmHNYKLMuWA8C3W3Tx94FAkEAl99DvzsM4lYAtVcHx/lBjt/Ao8At3QESF/gzbhz8kcTdYKCewymyzsSgpB/uCYWtplI8xDLBmjBdq8VPVELLawJBALnAOmZ8GUuYH738jp12NH6B3f7o9Wd6l+xdvEJ8JA1/UIQOh2nof2mDR1A7sWTATUmmCAN4y3ThZLTsWv1AYoA=");
        config.put("yeepayPublicKey","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSp6oYtbBu1E0/tj9pP2zEuXTfgF2vk1wQ7jStNnadm/DZT3TBK8wdQTwMtWILOmeHoSowOPXjNmqmhloE2wwTGWINd1Z2WN2GlU7CgWJL/NGv1iYX3TM5uuN45gYsecoXuVFvzVmYMSdASBjlNQPQzbK0PUc+8zsOl6gK1LU33wIDAQAB");

        CardInfoResult result=new CardInfoResult();
        QueryCardRequest request=new QueryCardRequest(config);
        try {
            QueryCarResponse response= request.setCardNo(cardNo).execute();
            result.setBankCode(response.getBankCode());
            result.setBankName(response.getBankName());
            result.setCardType(response.getCardType());
            result.setValid(response.getValid());
            return result;
        }catch (PayException e){
          log.error("查询卡信息错误",e);
        }
        return null;
    }


    @Override
    public BindResult submit(BindSubmitForm form) {

        BindResult result=new BindResult();
        PreBindRequest request=new PreBindRequest(config);
        request.setCardNo(form.getCardNo())
                .setIdCard(form.getIdCardNo())
                .setUserName(form.getName())
                .setMobile(form.getMobile())
                .setRequestNo(form.getRequestNo())
                .setIdentityId(form.getIdentityId());

        try {
            PreBindResponse response = (PreBindResponse) request.execute();
            result.setSuccess(response.isSuccess());
            result.setRequestId(form.getRequestNo());
        }catch (PayException e){
            result.setSuccess(false);
            result.setErrMsg(e.getMessage());
            result.setErrCode(e.getCode());
        }
        return result;
    }
    @Override
    public BindResult directBind(BindSubmitForm form) {

        BindResult result=new BindResult();
        DirectBindRequest request=new DirectBindRequest(config);
        request.setCardNo(form.getCardNo())
                .setIdCard(form.getIdCardNo())
                .setUserName(form.getName())
                .setMobile(form.getMobile())
                .setRequestNo(form.getRequestNo())
                .setIdentityId(form.getIdentityId());

        try {
            BindResponse response = request.execute();
            result.setSuccess(response.isSuccess());
            result.setRequestId(form.getRequestNo());
            result.setBindId(response.getBindId());
        }catch (PayException e){
            result.setSuccess(false);
            result.setErrMsg(e.getMessage());
            result.setErrCode(e.getCode());
        }
        return result;
    }

    @Override
    public BindResult comfirm(BindCardComfirmForm form) {
        BindResult result=new BindResult();
        BindComfirmRequest request=new BindComfirmRequest(config);
        request.setRequestNo(form.getRequestNo())
               .setvVlidateCode(form.getValidateCode());

        try {
            BindResponse response=    request.execute();
            result.setSuccess(response.isSuccess());
            result.setRequestId(form.getRequestNo());
            result.setBindId(response.getBindId());
        } catch (PayException e) {
            result.setSuccess(false);
            result.setErrMsg(e.getMessage());
            result.setErrCode(e.getCode());
        }

        return result;
    }

    /**
     * 针对YEEPAY没数据，本系统有数据的情况：解绑本地数据
     */
    @Override
    public boolean unBind(UnBindInterForm form) {
        UnBindRequest request=new UnBindRequest(config);
        boolean flag = false;

        String cardNo = form.getCardNo();
        String cardTop = cardNo.substring(0,6);
        String cardLast = cardNo.substring(cardNo.length() - 4);
        request.setCardTop(cardTop);
        request.setCardLast(cardLast);
        request.setIdentityId(form.getIdentityId());

        try {
            BaseResponse response=    request.execute();
            flag = response.isSuccess();
        } catch (PayException e) {
            log.error("解绑失败",e);
            if(("TZ7010017").equals(e.getCode())) {
            	flag = true;
            }
        }
        return flag;
    }

    @Override
    public BindResult query(String cardNo) {
        BindResult result=new BindResult();
        QueryBindRequest request=new QueryBindRequest(config);
        request.setRequestNo(cardNo);
        try {
            BindResponse response=    request.execute();
            result.setSuccess(response.isSuccess());
            result.setBindId(response.getBindId());
        } catch (PayException e) {
            log.error("查询绑卡失败",e);
        }

        return result;
    }
}
