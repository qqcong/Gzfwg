package com.bbbbb.pay.channel.weixin.util;


import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 信任管理器
 * 
 * 
 * @date 2014-11-21下午9:15:08
 */
public class MyX509TrustManager implements X509TrustManager {
//	private X509TrustManager sunJSSEX509TrustManager;

	public MyX509TrustManager()  {
	

	}

	// 检查客户端证书
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		 
	}

	// 检查服务器端证书
	public void checkServerTrusted(X509Certificate[] chain, String authType){
		
	}

	// 返回受信任的X509证书数组
	public X509Certificate[] getAcceptedIssuers() {
		  return null;
	}
}
