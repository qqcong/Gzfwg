package com.bbbbb.pay.channel.yeepay.request;

import com.bbbbb.pay.channel.yeepay.response.UnBindResponse;
import com.bbbbb.pay.common.pay.PayException;

import java.util.Map;

/**
 * 
 * 解绑请求
 */
public class UnBindRequest extends BaseRequest {
    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/unbind/request";
    public UnBindRequest(Map<String, String> config) {
        super(config);
    }



    @Override
    public UnBindResponse execute() throws PayException {
        String json=this.execute(REQ_URL);
        return new UnBindResponse(json);

    }

    /**
     * 卡号前6位
     * @param cardtop
     * @return
     */
    public UnBindRequest setCardTop(String cardtop){
        setProperty("cardtop",cardtop);
        return this;
    }

    /**
     * 卡号后4位
     * @param cardlast
     * @return
     */
    public UnBindRequest setCardLast(String cardlast){
        setProperty("cardlast",cardlast);
        return this;
    }

}
