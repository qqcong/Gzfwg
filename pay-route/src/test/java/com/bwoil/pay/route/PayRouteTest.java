package com.bbbbb.pay.route;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.gateway.Startup;
import com.bbbbb.pay.gateway.form.bind.BindComfirmForm;
import com.bbbbb.pay.gateway.form.bind.BindSubmitForm;
import com.bbbbb.pay.gateway.form.bind.QueryBindForm;
import com.bbbbb.pay.gateway.form.bind.UnbindForm;
import com.bbbbb.pay.gateway.form.pay.FirstPayForm;
import com.bbbbb.pay.gateway.form.pay.PayComfirmForm;
import com.bbbbb.pay.gateway.form.pay.PaySubmitForm;
import com.bbbbb.pay.gateway.form.pay.QueryPayForm;
import com.bbbbb.pay.gateway.result.bind.BindCardInfo;
import com.bbbbb.pay.gateway.result.bind.BindComfirmResult;
import com.bbbbb.pay.gateway.result.bind.BindSubmitResult;
import com.bbbbb.pay.gateway.result.bind.UnbindResult;
import com.bbbbb.pay.gateway.result.pay.PayOrderInfo;
import com.bbbbb.pay.gateway.result.pay.PaySubmitResult;
import com.bbbbb.pay.gateway.service.BindService;
import com.bbbbb.pay.gateway.service.PayService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Startup.class)
public class PayRouteTest {
	
	@Autowired
	private BindService bindService;
	
	@Autowired
    private PayService payService;
	
	@Test
	public void testSubmit() {
		BindSubmitForm form = new BindSubmitForm();
		form.setAppid("test");
		form.setChannel("YEEPAY");
		form.setUserId("12217");
		form.setRequestNo("112267");
		form.setMobile("?");
		form.setCardNo("?");
		form.setIdCardNo("?");
		form.setUserName("张泽");

		BindSubmitResult result= bindService.submit(form);		
		System.out.println("result*******************:" + JsonUtils.toString(result));
	}
	
	@Test
	public void testComfirm() {
		BindComfirmForm form = new BindComfirmForm();
		form.setRequestNo("112267");
		form.setValidateCode("053169");
		form.setAppid("test");

		BindComfirmResult result= bindService.comfirm(form);
		System.out.println("result*******************:" + JsonUtils.toString(result));
	}
	
	@Test
	public void testQuery() {
		QueryBindForm form = new QueryBindForm();
		form.setAppid("test");
		form.setUserId("12217");
		form.setCardNo("?");

		BindCardInfo result= bindService.query(form);
		System.out.println("result*******************:" + JsonUtils.toString(result));
	}
	
	@Test
	public void testUnbind() {
		UnbindForm form = new UnbindForm();
		
		form.setAppid("test");
		form.setUserId("12217");
		form.setCardNo("?");

		UnbindResult result= bindService.unbind(form);
		System.out.println("result*******************:" + JsonUtils.toString(result));
		System.out.println("result*******************:" + JsonUtils.toString(result));
		System.out.println("result*******************:" + JsonUtils.toString(result));
	}
	
	//支付
	@Test
	public void testPaySubmit() {
		PaySubmitForm form = new PaySubmitForm();
		
		form.setAppid("test");
		form.setUserId("12217");
		form.setOrderNo("121231537");
		form.setAmount("1");
		form.setProductName("IPHONEX");
		form.setCardNo("?");
		form.setCallbackUrl("https://www.baidu.com");

		PaySubmitResult result= payService.submit(form);
		System.out.println("result*******************:" + JsonUtils.toString(result));
		System.out.println("result*******************:" + JsonUtils.toString(result));
		System.out.println("result*******************:" + JsonUtils.toString(result));
	}
	
	@Test
	public void testPayConfirm() {
		PayComfirmForm form = new PayComfirmForm();
		
		form.setTransId("2018011117224404590199");
		form.setValidateCode("370169");	

		PayOrderInfo result= payService.comfirm(form);
		System.out.println("result*******************:" + JsonUtils.toString(result));
	}
	
	@Test
	public void testFirstPay() {
		FirstPayForm form = new FirstPayForm();
		
		form.setAppid("test");
		form.setUserId("444412218");
		form.setOrderNo("897616823");//直接绑卡的tran_id
		form.setRequestNo("89669123");//预支付的tran_id
		form.setAmount("1");
		form.setProductName("光汇云油");
		form.setCardNo("6217007200051666868");
		form.setCallbackUrl("https://www.baidu.com");
		form.setIdCardNo("430903198911223614");
		form.setUserName("张泽");
		form.setMobile("15387562818");
		
		PaySubmitResult result= payService.firstPay(form);
		System.out.println("result*******************:" + JsonUtils.toString(result));
	}
	
	
	@Test
	public void testPayQuery() {
		QueryPayForm form = new QueryPayForm();

		form.setTransId("2018011117224404590199");
		
		PayOrderInfo result= payService.query(form);
		System.out.println("result*******************:" + JsonUtils.toString(result));
	}
	


}
