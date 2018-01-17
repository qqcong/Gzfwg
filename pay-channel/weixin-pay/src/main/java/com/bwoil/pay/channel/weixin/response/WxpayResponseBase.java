package com.bwoil.pay.channel.weixin.response;




import com.bwoil.pay.channel.weixin.WeixinAccount;
import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.channel.weixin.exception.WxPayException;
import com.bwoil.pay.channel.weixin.util.Signature;
import com.bwoil.pay.channel.weixin.util.XMLParse;
import lombok.extern.apachecommons.CommonsLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by daoxing on 2016/9/23.
 */
@CommonsLog
public class WxpayResponseBase {

    // CONSTANTS
    public static final String KEY_KEY              = "KEY";
    public static final String KEY_SIGN             = "sign";
    public static final String KEY_SKIP_VERIFY_SIGN = "SKIP_VERIFY_SIGN";

    public static final String KEY_RETURN_CODE  = "return_code";
    public static final String KEY_RETURN_MSG   = "return_msg";
    public static final String KEY_RESULT_CODE  = "result_code";
    public static final String KEY_ERR_CODE     = "err_code";
    public static final String KEY_ERR_CODE_DES = "err_code_des";

    public static final String VALUE_SUCCESS    = "SUCCESS";
    public static final String VALUE_FAIL       = "FAIL";

    protected Boolean validity = null;


    protected String respString;


    private WeixinAccount mpAct;

    /**
     * This construstor automatically parse input as xml, and output properties. Meanwhile, detect the fails.
     * Notice that Properties does not support hierachy, so it go down if tag names are non-unique.
     * It is raw in present. If it really happens, a new response type and parser should be defined to cope with that.
     */
    public WxpayResponseBase(WeixinAccount mpAct,String xml)
            throws WechatApiException, WechatRunTimeException
    {

        this.mpAct=mpAct;
        this.respProp = XMLParse.xml2Properties(xml);

        if (!this.isReturnCodeSuccess()) {
            throw
                    new WechatRunTimeException(
                            this.respProp.getProperty(KEY_RETURN_CODE),
                            this.respProp.getProperty(KEY_RETURN_MSG)
                    );
        }
        if (!this.isResultCodeSuccess()) {
            throw
                    new WechatApiException(
                            this.respProp.getProperty(KEY_ERR_CODE),
                            this.respProp.getProperty(KEY_ERR_CODE_DES)
                    );
        }
        return;
    }



    /**
     * retrieve callback params or response content as String
     * @deprecated since 0.4.5 WxpayResponse no longer preserve the origin response body
     */
    public String getString()
    {
        return this.respString;
    }

    // PROPERTIES
    protected Properties respProp;


    /**
     * retrieve callback params or response content as Properties
     */
    public Properties getProperties()
    {
        return this.respProp ;
    }

    public final String getProperty(String key)
    {
        return this.respProp.getProperty(key);
    }

    public final Integer getIntProperty(String key)
    {
        String v = this.getProperty(key);
        return v!=null ? Integer.valueOf(v) : null;
    }

    public final Date getDateProperty(String key)
    {
        try
        {
            String v = this.getProperty(key);
            return       (v!=null) ? new SimpleDateFormat("yyyyMMddHHmmss").parse(v) : null  ;
        }
        catch (Exception ex)
        {
            // rarely occurs
            ex.printStackTrace();
            return null;
        }
    }
    public final Date getDateProperty(String key,String style)
    {
        try
        {
            String v = this.getProperty(key);
            return       (v!=null) ? new SimpleDateFormat(style).parse(v) : null  ;
        }
        catch (Exception ex)
        {
            // rarely occurs
            ex.printStackTrace();
            return null;
        }
    }


    // EXCEPTION
    /** 此字段是通信标识，非交易标识，交易是否成功需要查看 result_code 来判断
     */
    public boolean isReturnCodeSuccess()
    {
        return VALUE_SUCCESS.equals(this.getProperty(KEY_RETURN_CODE));
    }

    public boolean isResultCodeSuccess()
    {
        return VALUE_SUCCESS.equals(this.getProperty(KEY_RESULT_CODE));

    }

    public WxPayException getErrCode()
    {
        String errCode = this.getProperty(KEY_ERR_CODE);

        if (errCode != null) {
            return new WxPayException(errCode);
        }

        // else
        return null;
    }

    public String getErrCodeDes()
    {
        return this.getProperty(KEY_ERR_CODE_DES);

    }

    // VERIFY
    /**
     * verify response sign
     * @return true if passed (i.e. response content should be trusted), otherwise false
     */
    public boolean verify()
    {
        if (this.validity != null) {
            return (this.validity);
        }

        this.validity = Signature.checkIsSignValidFromResponseString(this.respProp,mpAct.getPayKey());

        return(this.validity);
    }


    protected static String materializeParamName(String template, Integer ... params)
    {
        String s = template;

        for (int i=0; i<params.length; i++) {
            s = s.replace("$" + i, Integer.toString(params[i]));
        }

        return(s);
    }
    @Override
    public  String toString(){
        return this.respString;
    }
}
