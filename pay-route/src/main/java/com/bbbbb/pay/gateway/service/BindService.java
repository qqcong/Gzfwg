package com.bbbbb.pay.gateway.service;


import com.bbbbb.pay.common.entity.BankCard;
import com.bbbbb.pay.common.entity.BindOrder;
import com.bbbbb.pay.common.pay.result.BindResult;
import com.bbbbb.pay.gateway.form.bind.*;
import com.bbbbb.pay.gateway.result.bind.BindCardInfo;
import com.bbbbb.pay.gateway.result.bind.BindComfirmResult;
import com.bbbbb.pay.gateway.result.bind.BindSubmitResult;
import com.bbbbb.pay.gateway.result.bind.UnbindResult;

import java.util.List;

/**
 * 
 * 获取渠道
 */
public interface BindService {



    /**
     * 绑卡信息提交
     * @param form
     * @return
     */
    BindSubmitResult submit(BindSubmitForm form);


    /**
     * 绑卡确认
     * @param form
     * @return
     */
   BindComfirmResult comfirm(BindComfirmForm form);

    /**
     * 直接绑卡，不经过短信验证
     * @param form
     * @return
     */
    BindComfirmResult directBind(BindSubmitForm form);

    /**
     *解绑
     */
    UnbindResult unbind(UnbindForm form);

    /**
     * 查询绑卡情况
     * @param form
     * @return
     */
    BindCardInfo query(QueryBindForm form);


    /**
     *
     */
    List<BindCardInfo> list(BindListForm form);
    
    /**
     * 绑卡成功，写bankcard和bindrecord
     * @param bindOrder
     * @return
     */
    public BankCard  wirteSuccessBindResult(BindOrder bindOrder);
}
