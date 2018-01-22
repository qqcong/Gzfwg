package com.bbbbb.pay.channel.weixin.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

public class MD5 {
	/**
	 * 用户支付或红包签名
	 * 
	 * @param parameters
	 * @param key
	 *            支付API密钥
	 * @return 签名
	 */
	public static String calculate(String characterEncoding,
			SortedMap<String, String> parameters, String key)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = parameters.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {// 已排序的参数拼接 a=xx&b=yy
			Entry<String, String> entry = (Entry<String, String>) it
					.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);// 最后加入支付密钥
		String s = sb.toString();
		String sign = MD5Encode(s, characterEncoding).toUpperCase();// 生成MD5，并转换成大写
		// System.out.println("pa-----\n"+s);
		return sign;
		// return key;

	}


	/**
	 * 用户支付或红包签名
	 *
	 * @param parameters
	 * @param authcode
	 *            支付API密钥
	 * @return 签名
	 */
	public static String calculate(
								   SortedMap<String, String> parameters, String authcode)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(authcode+"&");
		Set<Entry<String, String>> es = parameters.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {// 已排序的参数拼接 a=xx&b=yy
			Entry<String, String> entry = (Entry<String, String>) it
					.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append(authcode);// 最后加入支付密钥
		String s = sb.toString();
		String sign = MD5Encode(s, "UTF-8");// 生成MD5，并转换成大写
		// System.out.println("pa-----\n"+s);
		return sign;
		// return key;

	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * MD5加密
	 * 
	 * @param origin
	 * @param charsetname
	 * @return
	 */
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}
