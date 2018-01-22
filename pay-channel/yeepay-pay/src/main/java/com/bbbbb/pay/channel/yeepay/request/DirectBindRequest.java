package com.bbbbb.pay.channel.yeepay.request;


import com.bbbbb.common.framework.util.DateUtils;
import com.bbbbb.pay.channel.yeepay.response.BindResponse;
import com.bbbbb.pay.common.pay.PayException;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Date;
import java.util.Map;

/**
 * 直接绑卡，无需要验证
 */
@CommonsLog
public class DirectBindRequest extends BaseRequest {
    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/bindcard/unified";
    public DirectBindRequest(Map<String, String> config) {
        super(config);
        setProperty("idcardtype","ID");
        setProperty("requesttime", DateUtils.format(new Date()));
        setProperty("issms", "false");
        setProperty("isbindcard","true");
        setProperty("authtype","COMMON_THREE");
    }



    @Override
    public BindResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new BindResponse(json);
    }

    /**
     * 设置交易号
     * @param requestNo
     * @return
     */
    public DirectBindRequest setRequestNo(String requestNo){
        setProperty("requestno",requestNo);
        return this;
    }

    /**
     * 卡号
     * @param cardNo
     * @return
     */
    public DirectBindRequest setCardNo(String cardNo){
        setProperty("cardno",cardNo);
        return this;
    }

    /**
     * 身份证号
     * @param idcardno
     * @return
     */
    public DirectBindRequest setIdCard(String idcardno){
        setProperty("idcardno",idcardno);
        return this;
    }

    /**
     * 身份证姓名
     * @param userName
     * @return
     */
    public DirectBindRequest setUserName(String userName){
        setProperty("username",userName);
        return this;
    }

    /**
     * 手机号
     * @param phone
     * @return
     */
    public DirectBindRequest setMobile(String phone){
        setProperty("phone",phone);
        return this;
    }
}
