package com.bbbbb.pay.channel.baofu;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.bbbbb.pay.channel.baofu.request.*;
import com.bbbbb.pay.common.pay.result.CardInfoResult;
import com.bbbbb.pay.common.util.SerialNumberUtils;
import org.junit.Test;

import com.bbbbb.common.framework.util.Base64;
import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.channel.baofu.response.BaseResponse;
import com.bbbbb.pay.channel.baofu.response.BindResponse;
import com.bbbbb.pay.channel.baofu.response.PayResponse;
import com.bbbbb.pay.channel.baofu.response.PreBindResponse;
import com.bbbbb.pay.channel.baofu.response.PrePayResponse;
import com.bbbbb.pay.channel.baofu.response.QueryPayResponse;
import com.bbbbb.pay.common.constants.RefundType;
import com.bbbbb.pay.common.pay.PayException;

public class BaofuTest {
	
	
	@Test
	public  void testPreBind() throws PayException, IOException {
		Map<String, String> config = baseConfig();
		//String transId = "95997849-857b-4f08-a87f-791b38fd3be8";
    	String transId = UUID.randomUUID().toString();
		String accNo = "6226096551572608";
		String idCard = "441322198008140811";
		String idHolder = "陈道兴";
		String mobile = "13528822599";
		String payCode = "CMB";
		PreBindResponse response = preBind(config, transId, accNo, idCard, idHolder, mobile, payCode);
		System.out.println(transId);
		System.out.println("response:" + JsonUtils.toString(response));
	}
	@Test
	public void testCardBin()throws PayException, IOException {
		Map<String, String> config = baseConfig();
		CardBinQuery query=new CardBinQuery(config);
		String accNo = "6226096551572608";
		query.setCardNo(accNo);
		CardInfoResult cardInfoResult=query.execute();
		System.out.println(cardInfoResult);
	}
	//@Test
	public void testBindConfirm() throws Exception {
		Map<String, String> config = baseConfig();
		String smsCode = "012959";
		String transId = "95997849-857b-4f08-a87f-791b38fd3be8";
		BindResponse response = bindConfirm(config, smsCode, transId);
		System.out.println(JsonUtils.toString(response));

	}
	
	@Test
	public void testDirectBind() throws Exception {
		Map<String, String> config = baseConfig();

		String transId =SerialNumberUtils.generate();
		DirectBindRequest request = new DirectBindRequest(config);
		request.setAccNo("6226096551572608").setIdCard("441322198008140811").setIdHolder("陈道兴").setMobile("13528822599")
				.setPayCode("CMB").setTransId(transId);

		BindResponse response = request.execute();
		System.out.println(JsonUtils.toString(response));
	}
	@Test
	public void testUnbind() throws Exception{
		Map<String, String> config = baseConfig();
		String bindId = "20171229201151388035430";
		BaseResponse response=unbind(config, bindId);
		System.out.println(JsonUtils.toString(response));
	}
	
	@Test
	public void testQueryBind() throws Exception{
		Map<String, String> config = baseConfig();
		String accNo = "6217857000026729259";
		BindResponse response = querybind(config, accNo);
		System.out.println(JsonUtils.toString(response));
		
	}

	
	
	//@Test
	public void testPrePay() throws Exception{
		Map<String, String> config = baseConfig();
		String bindId = "20171219113849387471998";
		String clientIp = "192.168.101.30";
		String transId = "6d8191ad-d56d-4269-b6c6-02d45911a1d3";
		int txnAmt = 10;
		PrePayResponse response = prepay(config, bindId, clientIp, transId, txnAmt);
		System.out.println(transId);
		System.out.println(JsonUtils.toString(response));

	}
	
	
	//@Test
	public void testRefund() throws Exception {
		Map<String, String> config = baseConfig();
		RefundRequest refundRequest = new RefundRequest(config);
		String noticeUrl="www.baidu.com";
		int refundAmt=10;
		String refundOrderNo="1495909803";
		String refundReason="测试退款";
		String refundTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int refundType=RefundType.AUTH_PAY.getCode();
		String transId="6d8191ad-d56d-4269-b6c6-02d45911a1d3";
		String transSerialNo=SerialNumberUtils.generate();
		System.out.println(transSerialNo);
/*		String transSerialNo=UUID.randomUUID().toString();
*/		refundRequest.setNoticeUrl(noticeUrl);
		refundRequest.setRefundAmt(refundAmt);
		refundRequest.setRefundOrderNo(refundOrderNo);
		refundRequest.setRefundReason(refundReason);
		refundRequest.setRefundTime(refundTime);
		refundRequest.setRefundType(refundType);
		refundRequest.setTransId(transId);
		//refundRequest.setTransSerialNo(transSerialNo);
		BaseResponse execute = refundRequest.execute();
		System.out.println(JsonUtils.toString(execute));
		System.out.println(transSerialNo);
	}
	
	
	//@Test
	public void testQueryRefund() throws Exception {
		
		Map<String, String> config = baseConfig();
		String refundOrderNo="1495909803";
		String transSerialNo="ba4ad9fe-cd38-4e84-9abf-f6cf7cacd422";
		QueryRefundRequest request = new QueryRefundRequest(config);
		request.setRefundOrderNo(refundOrderNo);
		//request.setTransSerialNo(transSerialNo);
		BaseResponse response = request.execute();
		System.out.println(JsonUtils.toString(response));
		
	}
	
	//@Test
	public void testQueryPay() throws Exception {
		Map<String, String> config = baseConfig();
		String origTransId = "6d8191ad-d56d-4269-b6c6-02d45911a1d3";
		QueryPayResponse response = querypay(config, origTransId);
		System.out.println(JsonUtils.toString(response));
	}
	
	//@Test
	public void testPayConfirm() throws Exception {
		Map<String, String> config = baseConfig();
		String businessNo = "20171219114103010431159098062271";
		String smsCode = "850244";
		PayResponse response = payConfirm(config, businessNo, smsCode);
		System.out.println(JsonUtils.toString(response));
	}


	private  PreBindResponse  preBind(Map<String, String> config, String transId, String accNo, String idCard,
			String idHolder, String mobile, String payCode) throws PayException {
		PreBindRequest request = new PreBindRequest(config);
		request.setAccNo(accNo).setIdCard(idCard).setIdHolder(idHolder).setMobile(mobile)
				.setPayCode(payCode).setTransId(transId);

		PreBindResponse response = (PreBindResponse) request.execute();
		return response;
	}

	

	private BindResponse bindConfirm(Map<String, String> config, String smsCode, String transId) throws PayException {
		BindComfirmRequest bindConfirmRequest = new BindComfirmRequest(config);
		bindConfirmRequest.setSmsCode(smsCode);
		bindConfirmRequest.setTransId(transId);
		BindResponse response = bindConfirmRequest.execute();
		return response;
	}

	

	private BaseResponse unbind(Map<String, String> config, String bindId) throws PayException {
		UnBindRequest resquest = new UnBindRequest(config);
		resquest.setBindId(bindId);
		BaseResponse response = resquest.execute();
		return response;
	}

	private BindResponse querybind(Map<String, String> config, String accNo) throws PayException {
		QueryBindRequest resquest = new QueryBindRequest(config);
		resquest.setAccNo(accNo);
		BindResponse response = resquest.execute();
		return response;
	}
	
	
	private PrePayResponse prepay(Map<String, String> config, String bindId, String clientIp, String transId,
			int txnAmt) throws PayException {
		PrePayRequest resquest = new PrePayRequest(config);
		resquest.setBindId(bindId);
		resquest.setTransId(transId);
		resquest.setClientIp(clientIp);
		resquest.setTxnAmt(txnAmt);
		PrePayResponse response = resquest.execute();
		return response;
	}

	/*private static Map<String, String> baseConfig() throws IOException {
		byte[] bytes = org.aspectj.util.FileUtil.readAsByteArray(new File("E:\\baofu\\pro\\商户私钥.pfx"));
		byte[] bs = org.aspectj.util.FileUtil.readAsByteArray(new File("E:\\baofu\\pro\\baofu.cer"));
		Map<String, String> config = new HashMap<>();
		config.put("memberId", "1202731");
		config.put("privateKey", Base64.encode(bytes));
		config.put("publicKey", new String(bs));
		config.put("priKeyPass", "170830");
		config.put("terminalId", "38747");
		return config;
	}*/
/*	private static Map<String, String> baseConfig() throws IOException {
		byte[] bytes = org.aspectj.util.FileUtil.readAsByteArray(new File("e:\\baofu\\private-dev.pfx"));
		byte[] bs = org.aspectj.util.FileUtil.readAsByteArray(new File("e:\\baofu\\public-dev.cer"));
		Map<String, String> config = new HashMap<>();
		config.put("memberId", "100000276");
		config.put("privateKey", Base64.encode(bytes));
		config.put("publicKey", new String(bs));
		config.put("priKeyPass", "123456");
		config.put("terminalId", "100000990");
		config.put("test", "true");
		System.out.println(JsonUtils.toString(config));
		return config;
	}*/

	private static Map<String, String> baseConfig() throws IOException {
		byte[] bytes = org.aspectj.util.FileUtil.readAsByteArray(new File("e:\\baofu\\private-test.pfx"));
		byte[] bs = org.aspectj.util.FileUtil.readAsByteArray(new File("e:\\baofu\\public-test.cer"));
		Map<String, String> config = new HashMap<>();
		config.put("memberId", "1203291");
		config.put("privateKey", Base64.encode(bytes));
		config.put("publicKey", new String(bs));
		config.put("priKeyPass", "170830");
		config.put("terminalId", "38803");
		//config.put("test", "true");
		System.out.println(JsonUtils.toString(config));
		return config;
	}

/*	private static Map<String, String> baseConfig() throws IOException {
		byte[] bytes = org.aspectj.util.FileUtil.readAsByteArray(new File("e:\\baofu\\private-prod.pfx"));
		byte[] bs = org.aspectj.util.FileUtil.readAsByteArray(new File("e:\\baofu\\public-prod.cer"));
		Map<String, String> config = new HashMap<>();
		config.put("memberId", "1202731");
		config.put("privateKey", Base64.encode(bytes));
		config.put("publicKey", new String(bs));
		config.put("priKeyPass", "170830");
		config.put("terminalId", "38747");
		//config.put("test", "true");
		System.out.println(JsonUtils.toString(config));
		return config;
	}*/

	private PayResponse payConfirm(Map<String, String> config, String businessNo, String smsCode) throws PayException {
		PayComfirmRequest resquest = new PayComfirmRequest(config);
		resquest.setBusinessNo(businessNo);
		resquest.setSmsCode(smsCode);
		PayResponse response = resquest.execute();
		return response;
	}
	
	

	private QueryPayResponse querypay(Map<String, String> config, String origTransId) throws PayException {
		QueryPayRequest resquest = new QueryPayRequest(config);
		resquest.setOrigTransId(origTransId);
		QueryPayResponse response = resquest.execute();
		return response;
	}
	
	
	/*1.卡预绑定 
	2.卡绑定查询  查询参数为用户账户acc_no
	3.卡绑定确认  依赖步骤1中的transid,新增smscode
	4.卡查询  查询参数为acc_no
	5.预支付  传入参数bind_id,client_ip,商户号trans_id,txtamt金额
	6.查询支付结果 传入参数为商户号trans_id
	7.卡支付确认 传入参数为预支付时返回的business_no
	8.查询支付结果 传入参数为trans_id
	9.卡解除绑定  传入参数为bind_id
	10.卡绑定查询 传入参数为acc_no
	*/
	//@Test
	public void testAll() throws Exception {
		Map<String, String> config = baseConfig();
		String transId = SerialNumberUtils.generate();
		String accNo = "6226096551572609";
		String idCard = "441322198008140811";
		String idHolder = "赖柄州";
		String mobile = "17770141261";
		String payCode = "CMB";
		PreBindResponse preBind = preBind(config, transId, accNo, idCard, idHolder, mobile, payCode);
		BindResponse querybind = querybind(config, accNo);
		String smsCode="123456";
		BindResponse bindConfirm = bindConfirm(config, smsCode, transId);
		BindResponse querybind2 = querybind(config, accNo);
		String clientIp="192.168.101.30";
		int txnAmt=10;
		transId=SerialNumberUtils.generate();
		PrePayResponse prepay = prepay(config, querybind2.getBindId(), clientIp, transId, txnAmt);
		/*QueryPayResponse querypay = querypay(config, transId);*/
		PayResponse payConfirm = payConfirm(config, prepay.getBusinessNo(), smsCode);
		QueryPayResponse querypay2 = querypay(config, transId);
		
		System.out.println("预绑定："+JsonUtils.toString(preBind));
		System.out.println("查询绑定结果："+JsonUtils.toString(querybind));
		System.out.println("绑定确认："+JsonUtils.toString(bindConfirm));
		System.out.println("绑定确认后查询："+JsonUtils.toString(querybind2));
		System.out.println("预支付："+JsonUtils.toString(prepay));
		/*System.out.println(JsonUtils.toString(querypay));*/
		System.out.println("支付确认："+JsonUtils.toString(payConfirm));
		System.out.println("支付结果查询："+JsonUtils.toString(querypay2));
	}
	
	

}
