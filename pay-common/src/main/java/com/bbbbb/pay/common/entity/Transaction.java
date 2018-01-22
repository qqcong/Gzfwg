package com.bbbbb.pay.common.entity;

import com.bbbbb.pay.common.constants.Channel;
import com.bbbbb.pay.common.constants.TradeStatus;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 交易(支付)信息
 */
@Entity
@Table(name="pay_transaction")
@DynamicUpdate
@Data
public class Transaction {


    /**
     *支付流水号
     */
    @Id
    private String transId;

    /**
     * 客户
     * 请求单号
     */
    private String requestNo;
    /**
     *
     */
    @Column(name = "appid")
    private String appid;

    /**
     * 支付卡
     */
    private long cardId;
    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date created;
    /**
     * 是否已支付
     */
    @Column(name = "paid")
    private boolean paid=false;
    /**
     *商户订单号，适配每个渠道对此参数的要求，必须在商户系统内唯一
     */
    @Column(name = "order_no")
    private String orderNo;
    /**
     * 支付使用的第三方支付渠道 。
     */
    @ManyToOne
    @JoinColumn(name = "channel")
    private ChannelConfig channel ;
    /**
     * 订单总金额（必须大于0），单位为对应币种的最小货币单位，人民币为分。如订单总金额为 1 元， amount 为 100，么么贷商
     */
    @Column(name = "amount")
    private Integer amount=0;
    /**
     *实际支付金额
     */
    @Column(name = "pay_amount")
    private Integer payAmount=0;
    /**
     *支付完成时间
     */
    @Column(name = "time_paid")
    private Date timePaid;
    /**
     *第三方支付订单编号
     */
    @Column(name = "transaction_no")
    private String transactionNo;
    /**
     *发起支付请求客户端的 IPv4 地址，如: 127.0.0.1。
     */
    @Column(name = "client_ip")
    private String clientIp="127.0.0.1";
    /**
     *商品的标题，该参数最长为 32 个 Unicode 字符，银联全渠道（ upacp / upacp_wap ）限制在 32 个字节。
     */
    @Column(name = "subject")
    private String  subject="";
    /**
     *商品的描述信息，该参数最长为 128 个 Unicode 字符，yeepay_wap 对于该参数长度限制为 100 个 Unicode 字符。
     */
    @Column(name = "body")
    private String body;
    /**
     * 特定渠道发起交易时需要的额外参数，以及部分渠道支付成功返回的额外参数，详细参考 支付渠道 extra 参数说明 。
     */
    @Column(name = "extra")
    @Type(type="com.bbbbb.pay.common.entity.JsonType")
    private Map<String,String> extra;
    /**
     *订单失效时间，用 Unix 时间戳表示。时间范围在订单创建后的 1 分钟到 15 天，默认为 1 天，创建时间以服务器时间为准。
     * 微信对该参数的有效值限制为 2 小时内；银联对该参数的有效值限制为 1 小时内。
     */
    @Column(name = "time_expire")
    private Date timeExpire;
    /**
     *失败代码
     */
    @Column(name = "failure_code")
    private String failureCode;
    /**
     *失败描述
     */
    @Column(name = "failure_msg")
    private String failureMsg;
    /**
     *支付用户编号
     */
    @Column(name = "openid")
    private String openid;
    /**
     *订单是否已关闭
     */
    @Column(name = "is_closed")
    private boolean isClosed;
    /**
     *交易状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "trade_status")
    private TradeStatus tradeStatus;
    /**
     * 支付回调地址
     */
    private String callbackUrl;
    /**
     *是否已通知业务系统
     */
    private Boolean notified;
    @Column(name = "credential")
    @Type(type="com.bbbbb.pay.common.entity.JsonType")
    private Map<String,String> credential;

    public void addExtra(String key,String value){

        if(  this.extra==null){
            this.extra=new HashMap<>(10);
        }
        if(value!=null) {
            extra.put(key, value);
        }
    }

    public void addExtraAll(Map<String,?> map){

        if(map==null){
            return;
        }
        if(  this.extra==null){
            this.extra=new HashMap<>(10);
        }
        for (String key:map.keySet()){
            Object value=map.get(key);
            extra.put(key,value==null?"":value.toString());
        }
    }
}
