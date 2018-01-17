package com.bwoil.pay.channel.baofu.request;

import com.bwoil.common.framework.util.DateUtils;
import com.bwoil.pay.channel.baofu.response.QueryPayResponse;
import com.bwoil.pay.common.pay.PayException;

import java.util.Date;
import java.util.Map;

/**
 * @author chendx
 * 查询支付状态请求
 */
public class QueryPayRequest  extends  BaseRequest{
    public QueryPayRequest(Map<String, String> config) {
        super(config);
    }
    @Override
    protected String getTxnSubType() {
        return "06";
    }

    @Override
    public QueryPayResponse execute() throws PayException {
        String responseString=post();
        return new QueryPayResponse(responseString);
    }

    /**
     * 交易单喷发
     * @param origTransId
     * @return
     */
    public QueryPayRequest setOrigTransId(String origTransId){
        this.bodyParam.put("orig_trans_id",origTransId);
        return this;
    }
    public QueryPayRequest setOrigTradeDate(String origTradeDate){
        this.bodyParam.put("orig_trade_date",origTradeDate);
        return this;
    }

    /**
     * 交易日期
     * @param origTradeDate
     * @return
     */
    public QueryPayRequest setOrigTradeDate(Date origTradeDate){
        this.bodyParam.put("orig_trade_date", DateUtils.format(origTradeDate,"yyyyyMMddHHmmss"));
        return this;
    }

}
