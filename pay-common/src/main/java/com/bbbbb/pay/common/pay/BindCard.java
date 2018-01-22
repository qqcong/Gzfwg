package com.bbbbb.pay.common.pay;

import java.util.Map;

import com.bbbbb.pay.common.pay.form.BindCardComfirmForm;
import com.bbbbb.pay.common.pay.form.BindSubmitForm;
import com.bbbbb.pay.common.pay.form.UnBindInterForm;
import com.bbbbb.pay.common.pay.result.BindResult;
import com.bbbbb.pay.common.pay.result.CardInfoResult;

/**
 * 
 * 快捷支付绑卡接口
 */
public interface BindCard {

     /**
      * 获取卡信息
      * @param cardNo
      * @return
      */
     CardInfoResult cardInfo(String cardNo);

     /**
      * 提交绑卡接口
      * @param form  绑卡信息
      * @return 绑卡结果
      *
      */
     BindResult submit(BindSubmitForm form);

     /**
      * 确认绑卡
      * @param form
      * @return 绑卡结果
      */
     BindResult comfirm(BindCardComfirmForm form);


     /**
      * 直接绑卡，不需验证码
      * @param form
      * @return 绑卡结果
      */
     BindResult directBind(BindSubmitForm form);

     /**
      * 解绑
	  *	 YEEPAY有两种情形
	  *  1.YEEPAY已解绑，本平台未解绑
	  *  2.YEEPAY和本平台都未解绑
      * @param form 解绑信息
      * @return 是否需要在本平台解绑
      */
     boolean unBind (UnBindInterForm form);

     /**
      * 查询绑卡状态
      * @param cardNo 卡号
      * @return 绑卡结果
      */

     BindResult query(String cardNo);

     /**
      * 设置配置信息
      * @param config
      */
     void setConfig(Map<String,String> config);





}
