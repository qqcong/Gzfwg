package com.bwoil.pay.sdk;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 新生支付 SDK
 * @ClassName: PaySDK 
 * @Description: TODO 
 * @author wuzf 
 * @date 2017年12月19日 下午5:05:54
 */
public class PaySDK {

	@SuppressWarnings("unused")
	private static  String pubKey;
	
	@SuppressWarnings("unused")
	private static  String priKey;
	
	private static  PayClient payClient = null;
	
	private static String baseURL;
	
	private static String appid ;
	
	 public static void init(String pubKey ,String priKey, String baseURL, String appid){
		PaySDK.pubKey = pubKey;
		PaySDK.priKey = priKey;
		PaySDK.baseURL = baseURL;
		PaySDK.appid = appid;
		PaySDK.payClient = new PayClient(pubKey,priKey);
    }
	 public PaySDK(){
		 
	 }
	
	/**
	 * 签约请求
	 * @Title: signRequest 
	 * @Description: TODO 
	 * @param paramMap
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject signRequest(Map<String, String> paramMap){
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("userId", paramMap.get("merUserId"));			//用户编号*
		tempMap.put("requestNo", paramMap.get("merOrderId"));		//签约订单号*
		tempMap.put("mobile", paramMap.get("mobileNo"));			//签约短信验证码*
		tempMap.put("cardNo", paramMap.get("cardNo"));				//卡号*
		tempMap.put("bankCode", paramMap.get("bankCode"));			//银行卡简码
		tempMap.put("idCardNo", paramMap.get("identityCode"));		//身份证号*
		tempMap.put("userName", paramMap.get("holderName"));		//用户姓名*
		
		tempMap.put("appid", appid);								//appid*
		tempMap.put("sign", "md5");									//签名*
		tempMap.put("timestamp", String.valueOf(String.valueOf(System.currentTimeMillis())));	//请求时间*
		return payClient.execute(baseURL + "/api/fast/bind/submit", tempMap);
	}
	
	/**
	 * 签约确认
	 * @Title: signConfirm 
	 * @Description: TODO 
	 * @param paramMap
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject signConfirm(Map<String, String> paramMap){
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("requestNo", paramMap.get("hnapayOrderId"));	//签约订单号
		tempMap.put("validateCode", paramMap.get("smsCode"));		//签约短信验证码
		
		tempMap.put("appid", appid);								//appid*
		tempMap.put("sign", "md5");									//签名*
		tempMap.put("timestamp", String.valueOf(System.currentTimeMillis()));	//请求时间*
		return payClient.execute(baseURL + "/api/fast/bind/comfirm", tempMap);
	}
	
	/**
	 * 解约
	 * @Title: signCancel 
	 * @Description: TODO 
	 * @param paramMap
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject signCancel(Map<String, String> paramMap){
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("userId", paramMap.get("memberId"));
		tempMap.put("cardNo", paramMap.get("cardNo"));
		
		tempMap.put("appid", appid);								//appid*
		tempMap.put("sign", "md5");									//签名*
		tempMap.put("timestamp", String.valueOf(System.currentTimeMillis()));	//请求时间*
		return payClient.execute(baseURL + "/api/fast/bind/unbind", tempMap);
	}
	
	/**
	 * 签约查询
	 * @Title: signCancel 
	 * @Description: TODO 
	 * @param paramMap
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject signQurey(Map<String, String> paramMap){
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("userId", paramMap.get("userId"));			//用户ID
		tempMap.put("cardNo", paramMap.get("cardNo"));			//卡号
		tempMap.put("bindId", paramMap.get("merOrderId"));		//订单号
		
		tempMap.put("appid", appid);							//appid*
		tempMap.put("sign", "md5");								//签名*
		tempMap.put("timestamp", String.valueOf(System.currentTimeMillis()));	//请求时间*
		return payClient.execute(baseURL + "/api/fast/bind/query", tempMap);
	}
	
	/*=======================================  支付部分    =======================================*/
	
	/**
	 * 支付请求
	 * @Title: signCancel 
	 * @Description: TODO 
	 * @param paramMap
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject payRequest(boolean isFirstPay, Map<String, String> paramMap){
		
		String url = baseURL + "/api/fast/pay/submit";
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("userId", paramMap.get("merUserId"));		//用户id
		tempMap.put("orderNo", paramMap.get("orderNo"));		//支付外部订单号（支付商品订单号）*
		tempMap.put("requestNo", paramMap.get("hnapayOrderId"));//b2c请求支付系统编号
		if(null != paramMap.get("tranAmt") && !"".equals(paramMap.get("tranAmt"))){
			tempMap.put("amount", changeY2F(paramMap.get("tranAmt")));			//支付金额*
		}
		tempMap.put("productName", paramMap.get("goodsInfo"));	//商品名称*
		tempMap.put("ip", paramMap.get("merUserIp"));			//支付终端的IP地址
		tempMap.put("cardNo", paramMap.get("cardNo"));			//卡号*
		tempMap.put("callbackUrl", paramMap.get("notifyUrl"));	//支付回调地址*
		if(isFirstPay){
			url = baseURL + "/api/fast/pay/first";
			tempMap.put("mobile", paramMap.get("mobileNo"));		//手机号
			tempMap.put("bankCode", paramMap.get("bankCode"));		//银行简码
			tempMap.put("idCardNo", paramMap.get("identityCode"));	//身份证号*
			tempMap.put("userName", paramMap.get("holderName"));	//用户姓名*
			System.out.println("========== 此次支付为第一次支付  ============");
		}
		tempMap.put("appid", appid);								//appid*
		tempMap.put("sign", "md5");									//签名*
		tempMap.put("timestamp", String.valueOf(System.currentTimeMillis()));	//请求时间*
		return payClient.execute(url, tempMap);
	}
	
	/**
	 * 支付确认
	 * @Title: signCancel 
	 * @Description: TODO 
	 * @param paramMap
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject payConfirm(Map<String, String> paramMap){
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("transId", paramMap.get("hnapayOrderId"));				//签约订单号*
		tempMap.put("validateCode", paramMap.get("smsCode"));				//短信验证码*
		
		tempMap.put("appid", appid);										//appid*
		tempMap.put("sign", "md5");											//签名*
		tempMap.put("timestamp", String.valueOf(System.currentTimeMillis()));	//请求时间*
		return payClient.execute(baseURL + "/api/fast/pay/comfirm", tempMap);
	}
	
	/**
	 * 支付查询
	 * @Title: signCancel 
	 * @Description: TODO 
	 * @param paramMap
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject payQuery(Map<String, String> paramMap){
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("requestNo", paramMap.get("merOrderId"));				//签约订单号*
//		tempMap.put("orderNo", paramMap.get("merOrderId"));					//交易ID*
		
		tempMap.put("appid", appid);										//appid*
		tempMap.put("sign", "md5");											//签名*
		tempMap.put("timestamp", String.valueOf(System.currentTimeMillis()));	//请求时间*
		return payClient.execute(baseURL + "/api/fast/pay/query", tempMap);
	}
	
	/**
	 * 元转分
	 * @Title: changeY2F 
	 * @Description: TODO 
	 * @param amount
	 * @return
	 * String
	 * @throws
	 */
	public static String changeY2F(String amount){    
        int index = amount.indexOf(".");    
        int length = amount.length();    
        Long amLong = 0l;    
        if(index == -1){    
            amLong = Long.valueOf(amount + "00");    
        }else if(length - index >= 3){    
        	amount = String.format("%.2f", Double.parseDouble(amount));
            amLong = Long.valueOf((amount.substring(0, index+3)).replace(".", ""));    
        }else if(length - index == 2){    
            amLong = Long.valueOf((amount.substring(0, index+2)).replace(".", "") + 0);    
        }else{    
            amLong = Long.valueOf((amount.substring(0, index+1)).replace(".", "") + "00");    
        }    
        return String.valueOf(amLong);    
    }
	

	public static void main(String[] args) {
		
		//签约下单
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("merUserId", "123456");			
		tempMap.put("merOrderId", "09876543210");		
		tempMap.put("mobileNo", "13528822599");			
		tempMap.put("bankCode", "CMB");			
		tempMap.put("cardNo", "6226096586236954");				
		tempMap.put("identityCode", "441322198008140811");	
		tempMap.put("holderName", "陈道兴");	
  	System.out.println("签约下单返回：" + signRequest(tempMap));
		tempMap.clear();
		tempMap.put("hnapayOrderId", "09876543210");		
		tempMap.put("smsCode", "100861");			
//		System.out.println("签约确认返回：" + signConfirm(tempMap));
		tempMap.clear();
		tempMap.put("userId", "123456");			
		tempMap.put("cardNo", "");			
		tempMap.put("merOrderId", "2017122214432783680817");			
//		System.out.println("签约查询返回：" + signQurey(tempMap));
		tempMap.clear();
		tempMap.put("merUserId", "123456");		
		tempMap.put("merOrderId", "0987654965534218");		
		tempMap.put("hnapayOrderId", "13452882259987892");
		tempMap.put("tranAmt", "10");			
		tempMap.put("goodsInfo", "测试商品");	
		tempMap.put("merUserIp", "merUserIp");			
		tempMap.put("cardNo", "6226096586236954");			
		tempMap.put("notifyUrl", "https://www.baidu.com");	
		tempMap.put("channel", "BAOFU");			
		tempMap.put("mobileNo", "13528822599");		
		tempMap.put("bankCode", "CMB");		
		tempMap.put("identityCode", "441322198008140811");	
		tempMap.put("holderName", "陈道兴");	
//		System.out.println("支付请求返回：" + payRequest(false, tempMap));
		tempMap.clear();
		tempMap.put("hnapayOrderId", "2017122220020918898458");		
		tempMap.put("smsCode", "123456");			
//		System.out.println("支付确认返回：" + payConfirm(tempMap));
		tempMap.clear();
		tempMap.put("requestNo", "hnapayOrderId");
		tempMap.put("merOrderId", "2017122220020918898458");			
		System.out.println("支付查询返回：" + payQuery(tempMap));
	}
}
