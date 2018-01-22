package com.bbbbb.pay.common.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * 支付渠道配置信息
 * */
@Data
@Entity(name = "pay_channel_config")
public class ChannelConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "appid")
    private App app;
    @ManyToOne
    @JoinColumn(name = "channel_code")
    private PayChannel payChannel;
    private boolean enable;
    /**
     * 鉴权(绑卡)费用
     */
    private BigDecimal authFee;
    /**
     * 0.停用 1.正常 2系统异常
     */

    private int status;
    /**
     * 支付费率
     * 0<=X<1
     */
    private BigDecimal payFeeRate;

    /**
     *
     * 支付费用保底值（最低多少一笔）
     * x=0 不保底
     */
    private BigDecimal payFeeMin;

    /**
     * x<=0 不封顶
     *支付费用封顶值（最低多少一笔）
     */
    private BigDecimal payFeeMax;

    @Type(type="com.bbbbb.pay.common.entity.JsonType")
    private Map<String,String> config;

    /**
     * 计算支付所需费用
     * @param amount  支付金额（单位分）
     * @return
     */
   public  BigDecimal calculatePayFee(int amount){
       //最小费用
       BigDecimal min=payFeeMin;
       BigDecimal payFee= new BigDecimal(amount).divide(new BigDecimal(10000)).multiply(payFeeRate).setScale(0,BigDecimal.ROUND_HALF_DOWN);
       min=payFee.compareTo(payFeeMin)>0?payFee:payFeeMin;

       if(payFeeMax!=null&&payFeeMax.intValue()>0){
           min=min.compareTo(payFeeMax)>0?payFeeMax:min;
       }

       return  min;
   }


    public static void main(String[] args) {
        ChannelConfig config=new ChannelConfig();
        config.setPayFeeMax(new BigDecimal(0));
        config.setPayFeeMin(new BigDecimal(2));
        config.setPayFeeRate(new BigDecimal(0.1d));
        System.out.println(config.calculatePayFee(1000000*100));
    }

}
