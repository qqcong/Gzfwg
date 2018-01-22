package com.bbbbb.pay.channel.weixin.util;


import lombok.extern.apachecommons.CommonsLog;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;


/**
 * Http工具包，用于访问API，上传或下载微信素材
 * 
 * @author 陈道兴
 * @since 2.0
 */
// TODO Http.disableJvmHttpsCheck();
@CommonsLog
public class HttpTool {


    public static String post(String requestUrl, String body) {
        log.info("请求内容:"+body);
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
			conn.setConnectTimeout(20*1000);
			conn.setReadTimeout(20*1000);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "text/xml");
            conn.connect();
            // 当outputStr不为null时向输出流写数据
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;


            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return null;
    }



    /**
	 * 发送https请求,带微信支付证书
	 * @param requestUrl 请求地址
	
	 * @param body 提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String post4SSL(String requestUrl, String body,SSLSocketFactory sslSocketFactory) {
		try {


            log.info("请求内容:"+body);
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sslSocketFactory);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(20*1000);
			conn.setReadTimeout(20*1000);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "text/xml"); 
			conn.connect();
			// 当outputStr不为null时向输出流写数据
			if (null != body) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(body.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			
		
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			log.error("连接超时：{}", ce);
		} catch (Exception e) {
			log.error("https请求异常：{}", e);
		}
		return null;
	}
}
