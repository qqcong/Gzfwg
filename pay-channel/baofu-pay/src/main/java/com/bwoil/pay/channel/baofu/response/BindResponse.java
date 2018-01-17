package com.bwoil.pay.channel.baofu.response;

import com.bwoil.pay.common.pay.PayException;

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
        return (String)map.get("bind_id");
    }
}
