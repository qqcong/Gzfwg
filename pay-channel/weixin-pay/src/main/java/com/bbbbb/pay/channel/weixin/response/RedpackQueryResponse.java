package com.bbbbb.pay.channel.weixin.response;





import com.bbbbb.pay.channel.weixin.WeixinAccount;
import com.bbbbb.pay.channel.weixin.exception.WechatApiException;
import com.bbbbb.pay.channel.weixin.exception.WechatRunTimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by daoxing on 2016/10/10.
 */
public class RedpackQueryResponse extends WxpayResponseBase {


    public static final String KEY_MCH_BILL_NO              = "mch_billno";//订单号
    public static final String KEY_DETAIL_ID      = "detail_id";
    public static final String KEY_STATUS         = "status";
    public static final String KEY_SEND_TYPE  = "send_type";
    public static final String KEY_TOTAL_AMOUT        = "total_amount";
    public static final String KEY_TOTAL_NUM           = "total_num";
    public static final String KEY_WISHING        = "wishing";
    public static final String KEY_CLIENT_IP        = "client_ip";
    public static final String KEY_ACT_NAME       = "act_name";

    public static final String KEY_REASON       = "reason";
    public static final String KEY_REFUND_AMOUNT      ="refund_amount";
    public static final String KEY_REFUND_TIME      = "refund_time";
    public static final String KEY_REMARK      = "remark";
    public static final String KEY_OPEN_ID       = "openid";

    public static final String KEY_MCT_ID       = "mch_id";
    public static final String KEY_REC_TIME       = "rcv_time";
    public static final String KEY_HB_LIST     ="hblist";

    public RedpackQueryResponse(WeixinAccount mpAct, String xml) throws WechatApiException, WechatRunTimeException {
        super(mpAct, xml);
    }

    public  String getMchBillNo() {
        return  super.getProperty(KEY_MCH_BILL_NO);
    }

    public  String getDetailId() {
        return  super.getProperty(KEY_DETAIL_ID);
    }

    public  String getStatus() {
        return  super.getProperty(KEY_STATUS);
    }

    public  String getSendType() {
        return  super.getProperty(KEY_SEND_TYPE);
    }

    public  Integer getTotalAmout() {
        return (Integer)getProperty(0,Integer.class,KEY_TOTAL_AMOUT);
    }

    public  Integer getTotalNum() {
        return  super.getIntProperty(KEY_TOTAL_NUM);
    }

    public  String getWishing() {
        return  super.getProperty(KEY_WISHING);
    }

    public  String getClientIp() {
        return  super.getProperty(KEY_CLIENT_IP);
    }

    public  String getActName() {
        return  super.getProperty(KEY_ACT_NAME);
    }

    public  String getReason() {
        return  super.getProperty(KEY_REASON);
    }

    public  Integer getRefundAmount() {
        return  super.getIntProperty(KEY_REFUND_AMOUNT);
    }

    public  Date getRefundTime() {
       return (Date)getProperty(0,Date.class,KEY_REFUND_TIME);
       // return  super.getDateProperty(KEY_REFUND_TIME,"\"yyyy-MM-dd HH:mm:ss\"");
    }

    public  String getRemark() {
        return super.getProperty(KEY_REMARK);
    }

    public  String getOpenId() {
        return (String)getProperty(0,String.class,KEY_OPEN_ID);

    }

    public  String getMctId() {
        return super.getProperty(KEY_MCT_ID);
    }

    public Date getRecTime() {
        return (Date)getProperty(0,Date.class,KEY_REC_TIME);
    }

    public  String getHbList() {
        return super.getProperty(KEY_HB_LIST);
    }

    public <T> Object  getProperty(int index ,Class<T> type,String name)
    {

        if(this.respProp.containsKey("hblist")){
            Map p= (Map)this.respProp.get("hblist");

            if(p.containsKey("hbinfo_"+index)){
                p=(Map)p.get("hbinfo_"+index);
                Object o=p.get(name);
                if(o==null){
                    return null;
                }
                if(type.equals(String.class)){
                    return o.toString();
                }else if(type.equals(Integer.class)){
                    return Integer.parseInt(o.toString());
                }else if(type.equals(Date.class)){
                    try {
                        return   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(o.toString()) ;
                    } catch (ParseException e) {
                        return null;
                    }

                }else {
                    return o.toString();
                }
            }
        }

        return null;

    }
}
