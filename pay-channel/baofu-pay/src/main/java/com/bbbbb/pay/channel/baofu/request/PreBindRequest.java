package com.bbbbb.pay.channel.baofu.request;

import com.bbbbb.pay.channel.baofu.response.BaseResponse;
import com.bbbbb.pay.channel.baofu.response.PreBindResponse;
import com.bbbbb.pay.common.pay.PayException;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Map;

/**
 * 
 * 绑卡请求
 */
@CommonsLog
public class PreBindRequest extends BaseRequest  {
    public PreBindRequest(Map<String, String> config) {
        super(config);
        //证件类型固定01（身份证）
        bodyParam.put("id_card_type", "01");
        //以下是信用卡，暂时不用
        bodyParam.put("acc_pwd", "");
        //信用卡有效期
        bodyParam.put("valid_date", "");
        //3位，背面密码
        bodyParam.put("valid_no", "");
    }


    @Override
    protected String getTxnSubType() {
        return "11";
    }

    @Override
    public BaseResponse execute() throws PayException {
        String responseString=post();
        return new PreBindResponse(responseString);

    }

    /**
     * 设置交易号
     * @param transId
     * @return
     */
    public PreBindRequest setTransId(String transId){
        this.bodyParam.put("trans_id",transId);
        return this;
    }

    /**
     * 卡号
     * @param accNo
     * @return
     */
    public PreBindRequest setAccNo(String accNo){
        this.bodyParam.put("acc_no",accNo);
        return this;
    }

    /**
     * 身份证号
     * @param idCard
     * @return
     */
    public PreBindRequest setIdCard(String idCard){
        this.bodyParam.put("id_card",idCard);
        return this;
    }

    /**
     * 身份证姓名
     * @param idHolder
     * @return
     */
    public PreBindRequest setIdHolder(String idHolder){
        this.bodyParam.put("id_holder",idHolder);
        return this;
    }

    /**
     * 手机号
     * @param mobile
     * @return
     */
    public PreBindRequest setMobile(String mobile){
        this.bodyParam.put("mobile",mobile);
        return this;
    }

    /**
     * 银行编码
     * @param payCode
     * @return
     */
    public PreBindRequest setPayCode(String payCode){
        this.bodyParam.put("pay_code",payCode);
        return this;
    }
}
