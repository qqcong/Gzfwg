package com.bwoil.pay.common.pay.result;

import lombok.Data;

/**
 *
 * @author chendx
 * 绑定结果信息
 */
@Data
public class BindResult {

    /**
     * 绑定Id
     */
    private String bindId;

    /**
     * 是否绑定成功
     */
    private boolean success;
    /**
     * 错误编码
     */
    private String errCode;

    /**
     * 错误消息
     */
    private String errMsg;

    /**
     * 请求号
     */
    private String requestId;
}
