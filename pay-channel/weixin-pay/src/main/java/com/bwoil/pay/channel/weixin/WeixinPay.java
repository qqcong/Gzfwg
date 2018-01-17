package com.bwoil.pay.channel.weixin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.channel.weixin.request.APPUnifiedOrder;
import com.bwoil.pay.channel.weixin.request.JSAPIUnifiedOrder;
import com.bwoil.pay.channel.weixin.request.NativeUnifiedOrder;
import com.bwoil.pay.channel.weixin.request.OrderQuery;
import com.bwoil.pay.channel.weixin.request.UnifiedOrder;
import com.bwoil.pay.channel.weixin.response.OrderQueryResponse;
import com.bwoil.pay.channel.weixin.response.UnifiedOrderResponse;
import com.bwoil.pay.common.constants.Channel;
import com.bwoil.pay.common.pay.Pay;
import com.bwoil.pay.common.pay.SystemException;
import com.bwoil.pay.common.pay.form.PayForm;
import com.bwoil.pay.common.pay.result.PayResult;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class WeixinPay implements Pay {

	private Map<String, String> config;

	@Override
	public PayResult pay(PayForm payForm) {
		PayResult rst = new PayResult();
		Channel c = payForm.getChannel();
		UnifiedOrder uo = null;
		try {
			WeixinAccount wa = convertConfig();
			switch (c) {
				case WX_APP:
					uo = new APPUnifiedOrder(wa);
					break;
				case WX_PUB_QR:
					uo = new NativeUnifiedOrder(wa);
					//uo.setProductId(payForm.getProductId());
					break;
				case WX_PUB:
					uo = new JSAPIUnifiedOrder(wa);
					uo.setOpenid(payForm.getAccount());
					break;
				default:
					break;
			}
			if(uo != null) {
				uo.setBody(payForm.getBody());
				uo.setOutTradeNo(payForm.getOrderNo());
				uo.setSpbillCreateIp(payForm.getIp());
				uo.setTotalFee(payForm.getAmount());
				UnifiedOrderResponse r = uo.execute();
				rst = convertPayRep(r);
			}
		} catch (WechatApiException | WechatRunTimeException e) {
			log.error(e);
			e.printStackTrace();
		}
		return rst;
	}

	@Override
	public PayResult query(String orderId, String transactionNo) {
		OrderQuery query = new OrderQuery(convertConfig());
		query.setOutTradeNo(orderId);
		query.setTransactionId(transactionNo);
		try {
			OrderQueryResponse qres = query.execute();
			return convertOrderQuery(qres);
		} catch (WechatRunTimeException | WechatApiException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

	@Override
	public void checkConfig(Map<String, String> config) throws SystemException {

	}

	private WeixinAccount convertConfig() {
		OpenAccount c = new OpenAccount();
		c.setAppId(config.get("appId"));
		c.setCert(config.get("cert"));
		c.setMchId(config.get("mchId"));
		c.setNotifyUrl(config.get("notifyUrl"));
		c.setPayKey(config.get("payKey"));
		return c;
	}
	
	private PayResult convertPayRep(UnifiedOrderResponse r) {
		PayResult rst = new PayResult();
		if(StringUtils.equalsIgnoreCase("FAIL", r.getProperty("return_code"))) {
			rst.setErrCode(r.getProperty("return_code"));
			rst.setErrMsg(r.getProperty("return_msg"));
		} else {
			if(StringUtils.equalsIgnoreCase("FAIL", r.getProperty("result_code"))) {
				rst.setErrCode(r.getProperty("err_code"));
				rst.setErrMsg(r.getProperty("err_code_des"));
			} else {
				Map<String,String> credential = new HashMap<>();
				credential.put("prepayId", r.getProperty("prepay_id"));
				rst.setCredential(credential);
			}
		}
		return rst;
	}
	
	private PayResult convertOrderQuery(OrderQueryResponse qres) {
		PayResult rst = new PayResult();
		if(StringUtils.equalsIgnoreCase("FAIL", qres.getProperty("return_code"))) {
			rst.setErrCode(qres.getProperty("return_code"));
			rst.setErrMsg(qres.getProperty("return_msg"));
		} else {
			if(StringUtils.equalsIgnoreCase("FAIL", qres.getProperty("result_code"))) {
				rst.setErrCode(qres.getProperty("err_code"));
				rst.setErrMsg(qres.getProperty("err_code_des"));
			} else {
				Map<String,String> credential = new HashMap<>();
				credential.put("tradeState", qres.getProperty("trade_state"));
				rst.setCredential(credential);
				rst.setOrderNo(qres.getProperty("out_trade_no"));
				rst.setTransactionNo(qres.getTransactionId());
				rst.setPayAmount(qres.getCashFeeFen());
				rst.setTimePaid(qres.getTimeEnd());
			}
		}
		return rst;
	}
}
