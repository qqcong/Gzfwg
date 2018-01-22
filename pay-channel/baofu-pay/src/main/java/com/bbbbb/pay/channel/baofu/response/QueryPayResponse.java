package com.bbbbb.pay.channel.baofu.response;

import java.math.BigDecimal;

import com.bbbbb.pay.common.constants.TradeStatus;
import com.bbbbb.pay.common.pay.PayException;

/**
 * 
 * 查询支付状态响应
 */
public class QueryPayResponse extends BaseResponse {
    public QueryPayResponse(String responseString) throws PayException {
        super(responseString);
    }
    public String getOrigTradeDate(){
        return (String)map.get("orig_trade_date");
    }
    public String getOrigTransId(){
        return (String)map.get("orig_trans_id");
    }
    public String getTransNo(){
        return (String)map.get("trans_no");
    }
    public String getBusinessNo(){
        return (String)map.get("business_no");
    }
    /**
     * 交易成功后返回的金额
     * @return
     */
    public int getSuccessAmount(){
    	return new BigDecimal((String)map.get("succ_amt")).multiply(new BigDecimal(100)).intValue();
    }
    /**
     * 获取支付状态
     * 状态码取值(大写字母)：
     *S：交易成功
     *F：交易失败
     *I：处理中
     *FF:：交易失败
     *IMPORTANT：当状态码结果为FF时，非支付订单交易结果，如：必传参数缺失时，报文解析失败时，参数格式校验失败等。
     * @return
     */
    public String getOrderStat(){
       return (String) map.get("order_stat");
    }
    public TradeStatus getTradeStatus(){
        String orderStat=(String) map.get("order_stat");
        if("S".equals(orderStat)){
            return TradeStatus.SUCCESS;
        }else  if("F".equals(orderStat)){
            return TradeStatus.ERROR;
        }else  if("I".equals(orderStat)){
            return TradeStatus.FAIL;
        }else  if("FF".equals(orderStat)){
            return TradeStatus.ERROR;
        }
        return TradeStatus.WAIT;
    }
}
