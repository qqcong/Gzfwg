package com.bbbbb.pay.common.util;

import com.bbbbb.pay.common.pay.PayException;
import com.bbbbb.pay.common.pay.SystemException;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * 错误代码
 */
@CommonsLog
public class ErrorsUtils {
    private static Properties pss = new Properties();
    static {
        try {
            init();
        }catch (Exception e){
            log.error("加error.properties错误",e);
        }

    }
    public static void  init() throws IOException {
       /* InputStream insss =ErrorsUtils.class.getResourceAsStream("/errors.properties");
        pss.load(insss);*/

        pss.load(new InputStreamReader(ErrorsUtils.class.getClassLoader().getResourceAsStream("errors.properties"), "UTF-8"));
    }
    public static String getMessage(String code){
        return pss.getProperty(code);
    }
    public static SystemException buildSystemException(String code){
        String errorMsg=ErrorsUtils.getMessage(code);
        return new SystemException(code,errorMsg);
    }
    public static PayException buildPayException(String code){
        String c=code;
        if(code.startsWith("BF")){
            c=code.replaceAll("BF","BW");
        }
        String errorMsg=getMessage(c);
        return new PayException(c,errorMsg);
    }
    public static PayException buildPayException(String code,String message){
        String c=code;
        if(code.startsWith("BF")){
            c=code.replaceAll("BF","BW");
        }
        return new PayException(c,message);
    }
}
