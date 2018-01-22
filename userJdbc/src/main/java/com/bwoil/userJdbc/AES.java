package com.bbbbb.userJdbc;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {


	static String sKey = "XXnCRabDJZEw3SsytebvJAWFdj99IaGh";
	public static byte[] encrypt(String sSrc) throws Exception {
		String sKey = "XXnCRabDJZEw3SsytebvJAWFdj99IaGh";
/*		String sKey = "XXnCRabDJZEw3SsytebvJAWFdj99IaGh";
*/		sKey = paddingKey(sKey);
		sSrc = paddingValue(sSrc);
		if (sKey == null) {
			System.out.println("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.println("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		/*if (sSrc.length() % 16 == 0) {
			String parseByte2HexStr = parseByte2HexStr(encrypted);
			parseByte2HexStr += "c2ebefa8a5cd18677036c3a085f045bf";
			encrypted = parseHexStr2Byte(parseByte2HexStr);
		}*/
		return encrypted;
	}

	public static String decrypt(byte[] sSrc) throws Exception {
		/*String sKey = "XXnCRabDJZEw3SsytebvJAWFdj99IaGh";*/
		String sKey = "XXnCRabDJZEw3SsytebvJAWFdj99IaGh";
		sKey = paddingKey(sKey);
		String parseByte2HexStr = parseByte2HexStr(sSrc);
		/*if (parseByte2HexStr.toLowerCase().contains("c2ebefa8a5cd18677036c3a085f045bf")) {
			parseByte2HexStr = parseByte2HexStr.substring(0, parseByte2HexStr.length() - 32);
			sSrc = parseHexStr2Byte(parseByte2HexStr);
		}*/
		try {
			if (sKey == null) {
				System.out.println("Key为空null");
				return null;
			}
			if (sKey.length() != 16) {
				System.out.println("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			try {
				byte[] original = cipher.doFinal(sSrc);
				String originalString = new String(original, "utf-8");
				return originalString.trim();
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	private static String paddingKey(String needPadingKey) {
		char[] kys = needPadingKey.toCharArray();
		char[] keys = new char[16];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = '\0';
		}
		for (int i = 0; i < kys.length; i++) {
			keys[i % 16] = (char) ((int) (keys[i % 16]) ^ (int) (kys[i]));
		}
		String result = new String(keys);
		return result;
	}

	private static String paddingValue(String needPaddingValue) {
		char[] value = needPaddingValue.toCharArray();
		char c = (char) (16 - (value.length % 16));
		String result = strPad(value, 16 * ((int) Math.floor(value.length) / 16 + (value.length % 16 == 0 ? 2 : 1)), c);
		return result;
	}

	private static String strPad(char[] value, double d, char c) {
		int intValue = (new Double(d)).intValue();
		char[] paddingValue = new char[intValue];
		for (int i = 0; i < value.length; i++) {
			paddingValue[i] = value[i];
		}
		for (int i = value.length; i < d; i++) {
			paddingValue[i] = c;
		}
		String result = new String(value);
		return result;
	}

	public static String parseByte2HexStr(byte buf[]) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();

	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {

		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;

	}
	
	public static void main(String[] args) throws Exception {
		byte[] encrypt = AES.encrypt("1234567890123456");
		String parseByte2HexStr = AES.parseByte2HexStr(encrypt);
		System.out.println(parseByte2HexStr);
		/*String decrypt = AES.decrypt(AES.parseHexStr2Byte("d2e3b11dca49a100703581a31917881d".toLowerCase()));
		System.out.println(decrypt);*/
		/*String decrypt = AES.decrypt(parseHexStr2Byte("9B9114F34AA99DA4DBBB951C075CFBF44D0C2883E596815B2D5237DF63B1AE3E"));
		System.out.println(decrypt);*/
		
		String decrypt = AES.decrypt(parseHexStr2Byte("60E2D9496EA1E0F1ED3878F4F041D12F3F3F0474ED30D2BE3E3124DB4F2347B2"));
		System.out.println(decrypt);
		
		
	}

}
