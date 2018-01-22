package com.bbbbb.pay.channel.baofu.request;


import com.bbbbb.common.framework.util.Base64;
import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.common.pay.result.CardInfoResult;
import com.bbbbb.pay.common.util.HttpUtil;
import com.bbbbb.pay.common.util.SecurityUtil;
import com.bbbbb.pay.common.util.SerialNumberUtils;
import com.bbbbb.pay.common.util.rsa.RsaCodingUtil;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 卡号信息查询
 */
@CommonsLog
public class CardBinQuery  {
    protected String requestUrl="https://api.xinyan.com/biztransfer/product/bankcard/v1/bin/info";
    protected String testRequestUrl="http://test.xinyan.com/biztransfer/product/bankcard/v1/bin/info";

    private boolean isTest=false;
    /**
     * 头参数
     */
    protected Map<String,String> headParam = new HashMap<>(10);

    /**
     * 加密部分
     **/
    protected  Map<String,Object> bodyParam = new HashMap<>(10);

    /**
     * 商户私钥
     */
    protected String publicKey;
    /**
     * 宝付公钥
     */
    protected byte[] privateKey;

    /**
     * 私钥密
     */
    protected String priKeyPass="123456";

    public CardBinQuery(Map<String,String> config){

        privateKey= Base64.decode(config.get("privateKey"));
        publicKey=config.get("publicKey");
        priKeyPass=config.get("priKeyPass");

        String t=config.get("test");
        if(StringUtils.isNotBlank(t)&&"true".equals(t)){
            isTest=true;
        }

        String terminalId=config.get("terminalId");
        headParam.put("member_id", config.get("memberId"));
        headParam.put("terminal_id", terminalId);
        headParam.put("data_type", "json");


        //交易日期
        String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        bodyParam.put("member_id", headParam.get("member_id"));
        bodyParam.put("terminal_id", terminalId);
        bodyParam.put("trade_date", trade_date);
        bodyParam.put("industry_type", "C3");
        bodyParam.put("product_type", "0");
        bodyParam.put("trans_id", SerialNumberUtils.generate());


    }

    /**
     * 卡号
     * @param cardNo
     * @return
     */
    public CardBinQuery setCardNo(String cardNo){
        bodyParam.put("card_no", cardNo);
        return this;
    }
    protected   void encrypt(){

        String json= JsonUtils.toString(bodyParam);

        log.info("提交前数据:"+json);
        try {
            String base64str = SecurityUtil.Base64Encode(json);
            String dataContent = RsaCodingUtil.encryptByPriPfxStream(base64str,privateKey,priKeyPass);
            headParam.put("data_content",dataContent);
            log.info("提交数据:"+JsonUtils.toString(headParam));
        }catch (Exception e){
            log.error("宝付加密内容出错",e);
            throw new RuntimeException("宝付加密内容出错",e);
        }

    }

    /**
     * 执行查询返回卡信息
     * @return
     */
    public CardInfoResult execute(){
        String responseString=post();
        log.info("卡bin数据返回:"+responseString);
        Map map=JsonUtils.toMap(responseString);
        CardInfoResult result= new CardInfoResult();
        if( map.get("data")==null||"null".equals(map.get("data"))){
            return result;
        }
        Map data=(Map) map.get("data");
        result.setValid("VALID");
        result.setCardType("1".equals(data.get("card_type"))||"1".equals(data.get("card_type"))?"DEBIT":"CREDIT");
        result.setBankName(data.get("bank_description")+"");
        result.setBankCode(data.get("bank_id")+"");
        return result;
    }

    protected String post(){
        encrypt();
        String responseString  = HttpUtil.RequestForm(isTest?testRequestUrl:requestUrl, headParam);
        log.info("请求返回："+ responseString);
        return responseString;
    }
}
