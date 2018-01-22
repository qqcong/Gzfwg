package com.bbbbb.pay.channel.yeepay.request;


import com.bbbbb.common.framework.util.DateUtils;
import com.bbbbb.pay.channel.yeepay.response.PrePayResponse;
import com.bbbbb.pay.common.pay.PayException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class PrePayRequest extends BaseRequest {
    private static final String REQ_URL="https://jinrong.yeepay.com/tzt-api/api/bindpay/request";

    public PrePayRequest(Map<String, String> config) {
        super(config);
        setProperty("requesttime", DateUtils.format(new Date()));
    }



    @Override
    public PrePayResponse execute() throws PayException {
    	dataMap.put("registtime", DateUtils.format(new Date()));
    	dataMap.put("issetpaypwd", "0");
    	dataMap.put("terminalid", "1610000449592");
    	dataMap.put("lastloginterminalid", "1610000449592");
    	dataMap.put("productName", "光汇云油");
    	
        String json=this.execute(REQ_URL);
        return new PrePayResponse(json);
    }

    /**
     * 卡号前6位
     * @param cardtop
     * @return
     */
    public PrePayRequest setCardTop(String cardtop){
        setProperty("cardtop",cardtop);
        return this;
    }
    /**
     * 设置交易号
     * @param requestno
     * @return
     */
    public PrePayRequest setResquestNo(String requestno){
        setProperty("requestno",requestno);
        return this;
    }

    /**
     * 卡号后4位
     * @param cardlast
     * @return
     */
    public PrePayRequest setCardLast(String cardlast){
        setProperty("cardlast",cardlast);
        return this;
    }

    /**
     * 支付金额
     * @param amount 金额以元为单位(整型数据)并把分转换成元
     * @return
     */
    public PrePayRequest setAmount(int amount){
        setProperty("amount",new BigDecimal(amount).divide(new BigDecimal(100)).setScale(2).toString());
        return this;
    }
    public PrePayRequest setAmount(BigDecimal amount){
        setProperty("amount",amount.setScale(2).toString());
        return this;
    }

    public PrePayRequest setProductName(String productname){
        setProperty("productname",productname);
        return this;
    }
    public PrePayRequest setRegistTime(Date registtime){
        setProperty("registtime", DateUtils.format(registtime));
        return this;
    }
    public PrePayRequest setLastLoginTerminalId(String lastLoginTerminalId){
        setProperty("lastloginterminalid", lastLoginTerminalId);
        return this;
    }
    public PrePayRequest setCallBackUrl(String url){
        setProperty("callbackurl", url);
        return this;
    }


}
