package com.bwoil.pay.channel.baofu;

import com.bwoil.pay.channel.baofu.request.*;
import com.bwoil.pay.channel.baofu.response.BaseResponse;
import com.bwoil.pay.channel.baofu.response.BindResponse;
import com.bwoil.pay.channel.baofu.response.PreBindResponse;
import com.bwoil.pay.common.pay.BindCard;
import com.bwoil.pay.common.pay.PayException;
import com.bwoil.pay.common.pay.form.BindCardComfirmForm;
import com.bwoil.pay.common.pay.form.BindSubmitForm;
import com.bwoil.pay.common.pay.form.UnBindInterForm;
import com.bwoil.pay.common.pay.result.BindResult;
import com.bwoil.pay.common.pay.result.CardInfoResult;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Map;

/**
 * @author chendx
 * 宝付绑卡实现
 */
@CommonsLog
public class BaofuBindCard implements BindCard {

    private Map<String,String> config;
    @Override
    public void setConfig(Map<String, String> config) {
        this.config=config;
    }

    @Override
    public CardInfoResult cardInfo(String cardNo) {

        CardBinQuery query=new CardBinQuery(config);
        query.setCardNo(cardNo);
        CardInfoResult cardInfoResult=query.execute();
       return cardInfoResult;
    }

    @Override
    public BindResult submit(BindSubmitForm form) {

        BindResult result=new BindResult();
        PreBindRequest request=new PreBindRequest(config);
        request.setAccNo(form.getCardNo())
                .setIdCard(form.getIdCardNo())
                .setIdHolder(form.getName())
                .setMobile(form.getMobile())
                .setPayCode(form.getBankCode())
                .setTransId(form.getRequestNo());

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
        request.setAccNo(form.getCardNo())
                .setIdCard(form.getIdCardNo())
                .setIdHolder(form.getName())
                .setMobile(form.getMobile())
                .setPayCode(form.getBankCode())
                .setTransId(form.getRequestNo());

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
        request.setTransId(form.getRequestNo())
               .setSmsCode(form.getValidateCode());

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

    @Override
    public boolean unBind(UnBindInterForm form) {
        UnBindRequest request=new UnBindRequest(config);
        request.setBindId(form.getBindId());

        try {
            BaseResponse response=    request.execute();
           return response.isSuccess();
        } catch (PayException e) {
            log.error("解绑失败",e);
        }
        return false;
    }

    @Override
    public BindResult query(String cardNo) {
        BindResult result=new BindResult();
        QueryBindRequest request=new QueryBindRequest(config);
        request.setAccNo(cardNo);
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
