package com.bwoil.pay.channel.baofu.response;

import com.bwoil.common.framework.util.DateUtils;
import com.bwoil.common.framework.util.JsonUtils;
import com.bwoil.pay.common.pay.PayException;
import com.bwoil.pay.common.util.ErrorsUtils;
import com.bwoil.pay.common.util.SecurityUtil;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Date;
import java.util.Map;

/**
 * @author chendx
 * 宝付请求响应基类
 */
@CommonsLog
public abstract class BaseResponse {
    protected Map<String,Object> map;
    public BaseResponse(String responseString) throws PayException {

        //判断解密是否正确。如果为空则宝付公钥不正确
        if(responseString.isEmpty()){
            log.info("=====检查解密公钥错误！");
            throw  ErrorsUtils.buildPayException("BW00020");
        }

        try {
            responseString = SecurityUtil.Base64Decode(responseString);
        }catch (Exception e){
            throw  ErrorsUtils.buildPayException("BW00020");
            //  throw new PayException("1000","解密失败",e);
        }
        log.info("=====返回查询数据解密结果:"+responseString);
        map= JsonUtils.toMap(responseString);

        String respCode=(String)map.get("resp_code");
        String respMsg=(String)map.get("resp_msg");
        if("0000".equals(respCode)){
            log.info("操作成功");
        }else{
            log.error("操作失败,返回码:"+respCode+"-->返回信息:"+respMsg);
            throw  ErrorsUtils.buildPayException(respCode,respMsg);
            //throw new PayException(respCode,respMsg);
        }
    }
    public boolean isSuccess(){
        return   "0000".equals(getRespCode());
    }

    public String getVersion(){
       return (String)map.get("version");
    }
    public String getReqReserved(){
        return (String)map.get("req_reserved");
    }
    public String getAdditionalInfo(){
        return (String)map.get("additional_info");
    }

    public String getRespCode(){
        return (String)map.get("resp_code");
    }

    public String getRespMsg(){
        return (String)map.get("resp_msg");
    }

    public String getMemberId(){
        return (String)map.get("member_id");
    }
    public String getTerminalId(){
        return (String)map.get("terminal_id");
    }

    public String getTxnType(){
        return (String)map.get("txn_type");
    }
    public String getTxnSubType(){
        return (String)map.get("txn_sub_type");
    }
    public String getBizType(){
        return (String)map.get("biz_type");
    }
    /**
     *
     */
    public Date getTradeDate(){
        String date= map.get("trade_date").toString();
        return DateUtils.parseDate(date,"yyyyMMddHHmmss");
    }


    public String getTransSerialNo(){
        return (String)map.get("trans_serial_no");
    }

    public String getTransId(){
        return (String)map.get("trans_id");
    }
    public String getPayCardType(){
        return (String)map.get("pay_card_type");
    }
}
