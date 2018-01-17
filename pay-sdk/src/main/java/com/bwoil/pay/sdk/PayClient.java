package com.bwoil.pay.sdk;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author chendx
 * 支付客户端
 */
public class PayClient {
    private final static Log log= LogFactory.getLog(PayClient.class);
    /**
     * 支付系统公钥
     */
    private String pubKey="" ;
    
    /**
     * 商户私钥
     */
    private String priKey;
    
    public  PayClient(String pubKey ,String priKey){
        this.pubKey=pubKey;
        this.priKey=priKey;
    }

   public JSONObject execute(String url,Map<String,String> map ){
         map.remove("sign");
         String sign = SignUtils.sign(map,priKey);
         map.put("sign",sign);
         String json = postToPayRoute(url, JSON.toJSONString(map));
         JSONObject object = JSON.parseObject(json);
         sign = (String)object.get("sign");
         try {
            if(SignUtils.checkSign(object, sign, pubKey)){
                return object;
            }else {
                log.error("签名错误");
            }
            return object;
         }catch (Exception e){
             log.error("验证签名错误",e);
         }
        return object;
   }
   
    @SuppressWarnings("deprecation")
	public static String postToPayRoute(String url, String jsonStr) {
        String result = null;
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        post.setRequestHeader("Content-type", "application/json; charset=utf-8");
		System.out.println("请求支付路由json：" + jsonStr);
        post.setRequestBody(jsonStr);
        try {
            Integer status = client.executeMethod(post);
            log.error("status:" + status);
            if(status == 200){
                log.error("请求支付路由成功！"+"   返回内容：" + post.getResponseBodyAsString());
                result = post.getResponseBodyAsString();
            }else{
                log.error("请求支付路由失败！"+"   返回内容：" + post.getResponseBodyAsString());
            }
        } catch (HttpException e) {
            log.error("请求支付路由失败",e);
        } catch (IOException e) {
            log.error("请求支付路由失败",e);
        }
        finally{
            post.releaseConnection();//释放连接
        }
        return result;
    }
}
