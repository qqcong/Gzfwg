package com.bwoil.pay.bss.system;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

public class CommonTools {

	public static String bigNumAnd(String s1, String s2) {
		BigInteger hh = new BigInteger(s1, 16);
		BigInteger r = hh.and(new BigInteger(s2, 16));
		String ss = r.toString(16);
		return ss;
	}

	public static String fakeIdCardNo(String idCardNo) {
		if(StringUtils.isBlank(idCardNo)) {
			return "";
		}
		if(idCardNo.length() != 15 && idCardNo.length() != 18) {
			return "";
		}
		String str15 = "******";
		String str18 = "*********";
		idCardNo = idCardNo.length() == 18 ? idCardNo.substring(0, 6) + str18 + idCardNo.substring(14, 18)
				: idCardNo.substring(0, 6) + str15 + idCardNo.substring(12, 15);
		return idCardNo;
	}
	
	public static void main(String[] args) {
		String id = "123654789654123321";
		id = fakeIdCardNo(id);
		System.out.println(id);
	}

}
