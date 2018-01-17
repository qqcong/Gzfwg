package com.bwoil.pay.channel.baofu;

import com.bwoil.pay.channel.baofu.response.QueryPayResponse;
import com.bwoil.pay.common.dao.ChannelConfigDao;
import com.bwoil.pay.common.entity.ChannelConfig;
import com.bwoil.pay.common.pay.PayException;
import com.bwoil.pay.common.pay.PayNotity;
import com.bwoil.pay.common.pay.result.PayResult;
import com.bwoil.pay.common.util.rsa.RsaCodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  @author chendx
 *  宝付支付回调
 *  目前无用
 */
@Controller
@RequestMapping("/api/fast/pay")
public class BaofuPayNotify  {
    @Autowired
    private ChannelConfigDao channelConfigDao;
    @Autowired
    private PayNotity payNotity;
    @RequestMapping(value = "/baofu/notify/{configId}")
    public void payNotify(@PathVariable("configId")Long  configId, HttpServletRequest request, HttpServletResponse response) throws IOException{

           ChannelConfig config=  channelConfigDao.findById(configId);
           String  publicKey=config.getConfig().get("publicKey");
           String dataContent=request.getParameter("data_content");
           String responseString = RsaCodingUtil.decryptByPubCerText(dataContent,publicKey);
           PayResult result=new PayResult();
           try {
               QueryPayResponse payResponse = new QueryPayResponse(responseString);
               result.setTradeStatus(payResponse.getTradeStatus());
               result.setPayAmount(payResponse.getSuccessAmount());
               result.setTransactionNo(payResponse.getTransNo());
           }catch (PayException e){
               result.setErrCode(e.getCode());
               result.setErrMsg(e.getMessage());
           }
          payNotity.notify(result);
          response.getWriter().write("OK");

    }
}
