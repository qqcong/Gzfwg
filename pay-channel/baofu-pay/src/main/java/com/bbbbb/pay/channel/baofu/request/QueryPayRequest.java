package com.bbbbb.pay.channel.baofu.request;

import com.bbbbb.common.framework.util.DateUtils;
import com.bbbbb.pay.channel.baofu.response.QueryPayResponse;
import com.bbbbb.pay.common.pay.PayException;

import java.util.Date;
import java.util.Map;

/**
 * 
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
