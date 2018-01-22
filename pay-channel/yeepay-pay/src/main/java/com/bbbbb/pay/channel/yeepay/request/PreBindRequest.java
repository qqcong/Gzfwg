package com.bbbbb.pay.channel.yeepay.request;


import com.bbbbb.common.framework.util.DateUtils;
import com.bbbbb.pay.channel.yeepay.response.BaseResponse;
import com.bbbbb.pay.channel.yeepay.response.PreBindResponse;
import com.bbbbb.pay.common.pay.PayException;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Date;
import java.util.Map;

/**
 * @author chendx
 * 绑卡请求
 */
@CommonsLog
public class PreBindRequest extends BaseRequest  {

    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/bindcard/request";
    public PreBindRequest(Map<String, String> config) {
        super(config);
        setProperty("idcardtype","ID");
        setProperty("advicesmstype","MESSAGE");
        setProperty("requesttime", DateUtils.format(new Date()));

    }

    @Override
    public BaseResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new PreBindResponse(json);

    }

    /**
     * 设置交易号
     * @param requestNo
     * @return
     */
    public PreBindRequest setRequestNo(String requestNo){
        setProperty("requestno",requestNo);
        return this;
    }

    /**
     * 卡号
     * @param cardNo
     * @return
     */
    public PreBindRequest setCardNo(String cardNo){
        setProperty("cardno",cardNo);
        return this;
    }

    /**
     * 身份证号
     * @param idcardno
     * @return
     */
    public PreBindRequest setIdCard(String idcardno){
        setProperty("idcardno",idcardno);
        return this;
    }

    /**
     * 身份证姓名
     * @param userName
     * @return
     */
    public PreBindRequest setUserName(String userName){
        setProperty("username",userName);
        return this;
    }

    /**
     * 手机号
     * @param phone
     * @return
     */
    public PreBindRequest setMobile(String phone){
        setProperty("phone",phone);
        return this;
    }
}
