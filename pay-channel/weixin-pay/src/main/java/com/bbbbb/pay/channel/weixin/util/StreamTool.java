package com.bbbbb.pay.channel.weixin.util;

import lombok.extern.apachecommons.CommonsLog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 输入流与字符串处理工具
 *
 * 
 * @since 1.0
 */
@CommonsLog
public class StreamTool {



    /**
     * 将字符串转换成输入流
     *
     * @param str
     *            字符串
     * @return 输入流
     */
    public static InputStream toStream(String str) {
        InputStream stream = null;
        try {
            // UTF-8 解决网络传输中的字符集问题
            stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            log.error("转换输出Stream异常,不支持的字符集!!!");
            log.error(e.getLocalizedMessage(), e);
        }
        return stream;
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is
     *            输入流
     * @return 字符串
     * @throws IOException
     */
    public static String toString(InputStream is) {
        StringBuffer str = new StringBuffer();
        byte[] b = new byte[1024];

        try {
            for (int n; (n = is.read(b)) != -1;) {
                str.append(new String(b, 0, n));
            }
        }
        catch (IOException e) {
            log.error("读取输入流出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }
        return str.toString();
    }
}
