package com.bbbbb.pay.channel.yeepay.response;

import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.common.pay.PayException;
import com.bbbbb.pay.common.util.SecurityUtil;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@CommonsLog
public abstract class BaseResponse {

    public static final String VALUE_SUCCESS    = "SUCCESS";

    public static final String KEY_ERR_CODE     = "errorcode";
    public static final String KEY_ERR_MSG = "errormsg";
    protected Map<String,String> map;
    public BaseResponse(String responseString) throws PayException {

         this.map=JsonUtils.toTreeMap(responseString);

         if(!isSuccess()){
             throw  new PayException(map.get(KEY_ERR_CODE),map.get(KEY_ERR_MSG));
         }
    }

    /** 此字段是通信标识，非交易标识，交易是否成功需要查看 result_code 来判断
     */
    public boolean isSuccess()
    {
        return map.get(KEY_ERR_CODE)==null|| StringUtils.isBlank(map.get(KEY_ERR_CODE)) ||VALUE_SUCCESS.equals(map.get(KEY_ERR_CODE));
    }
    public Map<String,String> getMetaData(){
        return this.map;
    }
}
