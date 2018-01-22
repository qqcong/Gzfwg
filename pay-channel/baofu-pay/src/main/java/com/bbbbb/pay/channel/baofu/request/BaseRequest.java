package com.bbbbb.pay.channel.baofu.request;

import com.bbbbb.common.framework.util.Base64;
import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.channel.baofu.response.BaseResponse;
import com.bbbbb.pay.common.pay.PayException;
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
 * 操作基础类
 */
@CommonsLog
public abstract class BaseRequest {

    protected String requestUrl="https://public.baofoo.com/cutpayment/api/backTransRequest";
    protected String testRequestUrl="https://vgw.baofoo.com/cutpayment/api/backTransRequest";

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
    /**
     * 初始化
     * @param config
     */
   public BaseRequest(Map<String,String> config){

       privateKey= Base64.decode(config.get("privateKey"));
       publicKey=config.get("publicKey");
       priKeyPass=config.get("priKeyPass");

       String t=config.get("test");
       if(StringUtils.isNotBlank(t)&&"true".equals(t)){
           isTest=true;
       }

       String terminalId=config.get("terminalId");

       headParam.put("version", "4.0.0.0");
       headParam.put("member_id", config.get("memberId"));

       headParam.put("txn_type", "0431");
       headParam.put("txn_sub_type", getTxnSubType());
       headParam.put("data_type", "json");
       headParam.put("terminal_id", terminalId);

       //交易日期
       String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
       bodyParam.put("terminal_id", terminalId);
       bodyParam.put("txn_sub_type", headParam.get("txn_sub_type"));
       bodyParam.put("biz_type", "0000");
       bodyParam.put("member_id", headParam.get("member_id"));
       bodyParam.put("trans_serial_no", "bbbbb"+ SerialNumberUtils.generate());
       bodyParam.put("trade_date", trade_date);
       bodyParam.put("additional_info", "附加信息");
       bodyParam.put("req_reserved", "保留");
   }

    /**
     * 获取子交易类型
     * @return
     */
   protected abstract String getTxnSubType();

   protected   void encrypt(){

       String json=JsonUtils.toString(bodyParam);

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


   public  abstract BaseResponse execute() throws PayException;

   protected String post(){
       encrypt();
       String responseString  = HttpUtil.RequestForm(isTest?testRequestUrl:requestUrl, headParam);
       log.info("请求返回："+ responseString);
       responseString = RsaCodingUtil.decryptByPubCerText(responseString,publicKey);
       return responseString;
   }

}
