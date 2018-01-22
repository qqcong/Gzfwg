package com.bbbbb.pay.channel.baofu.request;

import com.bbbbb.pay.channel.baofu.response.PrePayResponse;
import com.bbbbb.pay.common.pay.PayException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *预支付请求
 */
public class PrePayRequest extends BaseRequest {

    private Map<String,String> riskContent = new HashMap<>(1);


    public PrePayRequest(Map<String, String> config) {
        super(config);
    }

    @Override
    protected String getTxnSubType() {
        return "15";
    }

    @Override
    public PrePayResponse execute() throws PayException {
        String responseString=post();
        return new PrePayResponse(responseString);
    }

    /**
     * 支付客户端IP地址
     * @param clientIp
     * @return
     */
    public PrePayRequest setClientIp(String clientIp){
        riskContent.put("client_ip",clientIp);
        this.bodyParam.put("risk_content",riskContent);
        return this;
    }
    /**
     * 设置交易号
     * @param transId
     * @return
     */
    public PrePayRequest setTransId(String transId){
        this.bodyParam.put("trans_id",transId);
        return this;
    }

    /**
     * 绑定卡id
     * @param bindId
     * @return
     */
    public PrePayRequest setBindId(String bindId){
        this.bodyParam.put("bind_id",bindId);
        return this;
    }

    /**
     * 支付金额
     * @param txnAmt 金额以分为单位(整型数据)并把元转换成分
     * @return
     */
    public PrePayRequest setTxnAmt(int txnAmt){
        this.bodyParam.put("txn_amt",txnAmt+"");
        return this;
    }
    public PrePayRequest setTxnAmt(BigDecimal txnAmt){
        this.bodyParam.put("txn_amt",txnAmt.multiply(new BigDecimal(100)).intValue()+"");
        return this;
    }
}
