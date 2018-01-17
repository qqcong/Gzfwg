package com.bwoil.pay.channel.yeepay.request;

import java.util.Map;

import com.bwoil.pay.channel.yeepay.response.PayResponse;
import com.bwoil.pay.common.pay.PayException;

public class FirstPaycomfirmRequest  extends BaseRequest{
	private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/firstpay/confirm";
	
    public FirstPaycomfirmRequest(Map<String, String> config) {
        super(config);
    }


    @Override
    public PayResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new PayResponse(json);
    }

    public FirstPaycomfirmRequest setRequestNo(String requestno){
        setProperty("requestno",requestno);
        return this;
    }

    /**
     * 短信验证码
     * @param validatecode
     * @return
     */
    public FirstPaycomfirmRequest setValidateCode(String validatecode){
        setProperty("validatecode",validatecode);
        return this;
    }
}
