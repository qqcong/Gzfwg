package com.bwoil.pay.channel.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bwoil.pay.channel.weixin.exception.WechatApiException;
import com.bwoil.pay.channel.weixin.exception.WechatRunTimeException;
import com.bwoil.pay.channel.weixin.response.Notify;
import com.bwoil.pay.common.constants.TradeStatus;
import com.bwoil.pay.common.dao.ChannelConfigDao;
import com.bwoil.pay.common.entity.ChannelConfig;
import com.bwoil.pay.common.pay.PayNotity;
import com.bwoil.pay.common.pay.result.PayResult;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Controller
@RequestMapping("/api/fast/pay")
public class WeixinPayNotify {

	@Autowired
	private ChannelConfigDao channelConfigDao;

	@Autowired
	private PayNotity payNotity;

	@RequestMapping(value = "/wx/notify/{configId}")
	public void payNotify(@PathVariable("configId") Long configId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ChannelConfig channelConfig = channelConfigDao.findById(configId);
		Map<String,String> config = channelConfig.getConfig();
		InputStream inputStream = request.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		StringBuffer buffer = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		// 释放资源
		inputStream.close();
		inputStreamReader.close();
		bufferedReader.close();

		OpenAccount oa = new OpenAccount();
		oa.setAppId(config.get("appId"));
		oa.setMchId(config.get("mchId"));
		oa.setCert(config.get("cert"));
		oa.setPayKey(config.get("payKey"));
		PayResult rst = new PayResult();
		try {
			Notify notify = new Notify(oa, buffer.toString());
			if (StringUtils.equalsIgnoreCase("FAIL", notify.getProperty("return_code"))) {
				rst.setErrCode(notify.getProperty("return_code"));
				rst.setErrMsg(notify.getProperty("return_msg"));
			} else {
				if (StringUtils.equalsIgnoreCase("FAIL", notify.getProperty("result_code"))) {
					rst.setErrCode(notify.getProperty("err_code"));
					rst.setErrMsg(notify.getProperty("err_code_des"));
				} else {
					rst.setTradeStatus(TradeStatus.SUCCESS);
					rst.setPayAmount(notify.getCashFee());
					rst.setTransactionNo(notify.getTransactionId());
				}
			}
			payNotity.notify(rst);

		} catch (WechatApiException | WechatRunTimeException e) {
			log.error(e);
			e.printStackTrace();
			response.getWriter().write(getRespErrorText(e.getMessage()));
		}
		response.getWriter().write(getRespSuccText());
	}

	private String getRespSuccText() {
		return "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
	}

	private String getRespErrorText(String error) {
		return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[" + error
				+ "]]></return_msg></xml>";
	}

}
