package com.bbbbb.pay.channel.yeepay;

import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.channel.yeepay.response.PayResponse;
import com.bbbbb.pay.channel.yeepay.util.AES;
import com.bbbbb.pay.channel.yeepay.util.EncryUtil;
import com.bbbbb.pay.channel.yeepay.util.RSA;
import com.bbbbb.pay.common.dao.ChannelConfigDao;
import com.bbbbb.pay.common.entity.ChannelConfig;
import com.bbbbb.pay.common.pay.PayException;
import com.bbbbb.pay.common.pay.PayNotity;
import com.bbbbb.pay.common.pay.result.PayResult;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.nio.charset.Charset;
import java.util.Map;

import static com.bbbbb.pay.channel.yeepay.request.BaseRequest.formatString;

/**
 *  
 *  宝付支付回调
 *  目前无用
 */
@Controller
@RequestMapping("/api/fast/pay")
@CommonsLog
public class YeePayNotify {
    @Autowired
    private ChannelConfigDao channelConfigDao;
    @Autowired
    private PayNotity payNotity;
    @RequestMapping(value = "/yeepay/notify/{configId}")
    public void payNotify(@PathVariable("configId")Long  configId, HttpServletRequest request, HttpServletResponse response) throws Exception{

        ChannelConfig config=  channelConfigDao.findById(configId);
        String  yeepayPublicKey=config.getConfig().get("yeepayPublicKey");
        String  merchantPrivateKey=config.getConfig().get("yeepayPublicKey");
       String responseBody= StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        //将String转化为Map
        Map<String, String> jsonMap= JsonUtils.toTreeMap(responseBody);


        String dataFromYeepay		= formatString(jsonMap.get("data"));
        String encryptkeyFromYeepay	= formatString(jsonMap.get("encryptkey"));
        //验证签名
        boolean signMatch = EncryUtil.checkDecryptAndSign(dataFromYeepay, encryptkeyFromYeepay,
                yeepayPublicKey, merchantPrivateKey);
        //签名不正确
        if(!signMatch) {
            log.error("签名错误");
            throw new RuntimeException("签名错误");
        }

        String yeepayAESKey		= RSA.decrypt(encryptkeyFromYeepay, merchantPrivateKey);
        String decryptData		= AES.decryptFromBase64(dataFromYeepay, yeepayAESKey);
        log.info("易宝解密后数据:"+decryptData);
        PayResult result=new PayResult();
           try {
               PayResponse payResponse = new PayResponse(decryptData);
               result.setTradeStatus(payResponse.getTradeStatus());
               result.setPayAmount(payResponse.getSuccessAmount());
               result.setTransactionNo(payResponse.getBusinessNo());
           }catch (PayException e){
               result.setErrCode(e.getCode());
               result.setErrMsg(e.getMessage());
           }
          payNotity.notify(result);
          response.getWriter().write("SUCCESS");

    }
}
