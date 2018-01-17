package com.bwoil.pay.common.util;

import com.bwoil.common.framework.util.DateUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

/**
 * @author chendx
 * 序列号生成器
 */
public class SerialNumberUtils {

    public static String generate(){
        Date now=new Date();
        String s=DateUtils.format(now,"yyyyMMddHHmmssSSS");
        return s+ RandomUtils.nextLong(10000L,99999L);
    }
}
