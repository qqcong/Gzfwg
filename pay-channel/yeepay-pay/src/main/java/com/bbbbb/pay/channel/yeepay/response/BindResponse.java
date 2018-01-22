package com.bbbbb.pay.channel.yeepay.response;

import com.bbbbb.pay.common.pay.PayException;

/**
 * @author chendx
 * 绑卡结果响应
 */
public class BindResponse extends BaseResponse {
    public BindResponse(String responseString) throws PayException {
        super(responseString);
    }

    /**
     * 获取绑定id
     * @return
     */
    public String getBindId(){
        return getCardTop()+"_"+getCardLast();
    }

    public String getRequestNo(){
        return map.get("requestno");
    }
    public String getBankCode(){
        return map.get("bankcode");
    }
    public String getStatus(){
        return map.get("status");
    }
    public String getCardTop(){
        return map.get("cardtop");
    }
    public String getCardLast(){
        return map.get("cardlast");
    }
}
