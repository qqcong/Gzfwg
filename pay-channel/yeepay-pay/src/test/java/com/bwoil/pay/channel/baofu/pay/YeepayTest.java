package com.bbbbb.pay.channel.baofu.pay;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.channel.yeepay.request.BindComfirmRequest;
import com.bbbbb.pay.channel.yeepay.request.DirectBindRequest;
import com.bbbbb.pay.channel.yeepay.request.PayComfirmRequest;
import com.bbbbb.pay.channel.yeepay.request.PreBindRequest;
import com.bbbbb.pay.channel.yeepay.request.PrePayRequest;
import com.bbbbb.pay.channel.yeepay.request.QueryBindRequest;
import com.bbbbb.pay.channel.yeepay.request.QueryCardRequest;
import com.bbbbb.pay.channel.yeepay.request.QueryPayRequest;
import com.bbbbb.pay.channel.yeepay.request.UnBindRequest;
import com.bbbbb.pay.channel.yeepay.response.BaseResponse;
import com.bbbbb.pay.channel.yeepay.response.BindResponse;
import com.bbbbb.pay.channel.yeepay.response.PayResponse;
import com.bbbbb.pay.channel.yeepay.response.PreBindResponse;
import com.bbbbb.pay.channel.yeepay.response.PrePayResponse;
import com.bbbbb.pay.channel.yeepay.response.QueryCarResponse;
import com.bbbbb.pay.common.pay.PayException;

public class YeepayTest {
	
	public Map<String,String> config = new HashMap<String, String>();

	public static void main(String[] args) {

	}	
	
	public void setConfig() {
        config.put("merchantno","10000449592");
        config.put("merchantPrivateKey","MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIVbHgGK4dLuoQkTvdEJ4ozAKxhgfoyAOJ72PG5r1/fmy83CD4h6z5suNi+KR7EcCZmbgqxm1o6K9bzywZpnrlaX9geY74vElsQjpkCMsKDAUPFagid4/tC4bxeFfOAmzgQdGKVHdn+4pBqvG2EdXXaaK/uIxl6rfylcuPVymhPTAgMBAAECgYB1VnwrfUlAcQmu0/kD8r+tevUwaM9Qzw2DCUSZIDkSfrg63pMOUolTkLDK2dBFDVeBGi07hu0o1SpuS+d/9dSWQld0VQMn5wVCxLAiWCs1RL9d9lMszAEDNbdAL+CN68X4Ve89uqRJ/qvOZOkhDZu5kS05CpjsA/pKgv/ce9hHuQJBANXHRvPAqfHSJvMa2qdAYrivWU7fTctWFLbqdhrg7EuGuMcl0WxcKzH2eHIofar8fXeLX39I0TAip0nYl+ML67UCQQCfsaVxWJvW2yQBsPAQPJC6UM5vMJnVhGfSd4e6s8H5PsLeMTZFEC3GQZvTCO0M6qA+scV64NH0x04vMIIpKAZnAkAUXvHm5lQLZBAsGEH/sAX4PVVQS70ZBDHjIEJy2z4JEGRpLbRgFARVtXvSvQMEmRzHmHNYKLMuWA8C3W3Tx94FAkEAl99DvzsM4lYAtVcHx/lBjt/Ao8At3QESF/gzbhz8kcTdYKCewymyzsSgpB/uCYWtplI8xDLBmjBdq8VPVELLawJBALnAOmZ8GUuYH738jp12NH6B3f7o9Wd6l+xdvEJ8JA1/UIQOh2nof2mDR1A7sWTATUmmCAN4y3ThZLTsWv1AYoA=");
        config.put("yeepayPublicKey","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSp6oYtbBu1E0/tj9pP2zEuXTfgF2vk1wQ7jStNnadm/DZT3TBK8wdQTwMtWILOmeHoSowOPXjNmqmhloE2wwTGWINd1Z2WN2GlU7CgWJL/NGv1iYX3TM5uuN45gYsecoXuVFvzVmYMSdASBjlNQPQzbK0PUc+8zsOl6gK1LU33wIDAQAB");
	}
	
	//1.查询银行卡
	@Test
    public void TestCardInfo() {		 
		String cardNo = "?"; 
		setConfig();		 
        QueryCardRequest request=new QueryCardRequest(config);        
		try {
			QueryCarResponse response = request.setCardNo(cardNo).execute();
			System.out.println(JsonUtils.toString(response));
		} catch (PayException e) {
			
		}	     
    }
	   
	//2.有短验绑卡
	@Test
    public void TestSubmit() {
		setConfig();
		
    	String cardNo = "6217007200051666868";
    	String idCardNo = "430903198911223614";
    	String userName = "张泽";
    	String mobile = "15387562818";
    	String requestNo = "112261";
    	String identityId = "1211221";
        PreBindRequest request=new PreBindRequest(config);
        request.setCardNo(cardNo)
                .setIdCard(idCardNo)
                .setUserName(userName)
                .setMobile(mobile)
                .setRequestNo(requestNo)
                .setIdentityId(identityId);

        try {
            PreBindResponse response = (PreBindResponse) request.execute();
            System.out.println(JsonUtils.toString(response));
        }catch (PayException e){

        }
    }
		
	//3.验绑卡确认
	@Test
    public void TestCardConfirm() {
		setConfig();		
		String requestNo = "112261";
		String code = "862030";//短信验证码
        BindComfirmRequest request=new BindComfirmRequest(config);
        request.setRequestNo(requestNo)
               .setvVlidateCode(code);

        try {
            BindResponse response=    request.execute();
            System.out.println(JsonUtils.toString(response));
        } catch (PayException e) {

        }
    }
	
	//4.查询绑卡记录
	@Test
    public void TestQuery() {
		setConfig();	
    	String requestNo = "?";
        QueryBindRequest request=new QueryBindRequest(config);
        request.setRequestNo(requestNo);
        try {
            BindResponse response=    request.execute();
            System.out.println(JsonUtils.toString(response));
        } catch (PayException e) {

        }
    }
	
	//5.无短信验证码快捷绑卡(易宝的统一鉴权绑卡请求)
	@Test
    public void TestDirectBind() {
		setConfig();	
		
		String cardNo = "?";
    	String idCardNo = "?";
    	String userName = "?";
    	String mobile = "?";
    	String requestNo = "?";
    	String identityId = "?";
        DirectBindRequest request=new DirectBindRequest(config);
        request.setCardNo(cardNo)
                .setIdCard(idCardNo)
                .setUserName(userName)
                .setMobile(mobile)
                .setRequestNo(requestNo)
        		.setIdentityId(identityId);

        try {
            BindResponse response = request.execute();
            System.out.println(JsonUtils.toString(response));
        }catch (PayException e){
        }
    }	
	
	//6.解绑
	@Test
    public void TestUnBind() {
		setConfig();	
        UnBindRequest request=new UnBindRequest(config);
        
        String account = "6217007200051666868";
        String cardLast = account.substring(account.length() - 4);
        String identityId = "12247";
        request.setCardTop(account.substring(0,6));
        request.setCardLast(cardLast);
        request.setIdentityId(identityId);

        try {
            BaseResponse response=    request.execute();
            System.out.println(JsonUtils.toString(response));
        } catch (PayException e) {
        	
        }
    }  	
	 
	
	//7.支付接口
	@Test
    public void TestPay() {
		setConfig();	
    	
		String account = "?";
		String requestNo = "?";
		int amount = 1;
		String identityId = "?";
        PrePayRequest request=new PrePayRequest(config);
        String cardLast = account.substring(account.length() - 4);
        request.setCardTop(account.substring(0,6)).setCardLast(cardLast)
        .setResquestNo(requestNo).setAmount(amount).setIdentityId(identityId);

        try {
            PrePayResponse response= request.execute();
            System.out.println(JsonUtils.toString(response));
        }catch (PayException e){

        }
    }
    
	//8.支付确认接口
	@Test
    public void TestPayConfirm() {
		setConfig();	
    	String requestNo = "?";
    	String smsCode = "?";//短信验证码 
        PayComfirmRequest request=new PayComfirmRequest(config);
        request.setRequestNo(requestNo).setValidateCode(smsCode);
        try {
            PayResponse response= request.execute();
            System.out.println(JsonUtils.toString(response));
        }catch (PayException e){

        }
    }
	
	//9.先调直接绑卡，再调用pay接口(有短验充值)
	@Test
	public void TestFirstPay() {
		setConfig();
	    this.TestDirectBind();
	    this.TestPay();
	 
	}
	
	//10.充值记录查询服务  
	@Test
    public void TestPayQuery() {
		setConfig();	
    	String requestNo = "?";

        QueryPayRequest request=new QueryPayRequest(config);
        request.setOrderId(requestNo);
        try {
            PayResponse response=   request.execute();
            System.out.println(JsonUtils.toString(response));
        }catch (PayException e){

        }
    }
	    
}
