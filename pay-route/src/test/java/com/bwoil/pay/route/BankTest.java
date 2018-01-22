package com.bbbbb.pay.route;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.pay.common.util.SerialNumberUtils;
import com.bbbbb.pay.gateway.Startup;
import com.bbbbb.pay.gateway.form.bank.BankListForm;
import com.bbbbb.pay.gateway.form.bind.BindSubmitForm;
import com.bbbbb.pay.gateway.result.bind.BindComfirmResult;
import com.bbbbb.pay.gateway.result.bind.BindSubmitResult;
import com.bbbbb.pay.gateway.service.BankService;
import com.bbbbb.pay.gateway.service.BindService;
import com.bbbbb.userJdbc.CardBean;
import com.bbbbb.userJdbc.DataImport;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class BankTest {

    @Autowired
    private BankService bankService;

    @Autowired
    private BindService bindService;
   // @Test
    public void test1(){
        BankListForm form=new BankListForm();
        form.setAppid("test");
        form.setAmount("100");
       System.out.println(JsonUtils.toString(bankService.list(form)));
    }

    @Test
    public void test2(){
        BindSubmitForm form=new BindSubmitForm();
        form.setAppid("test");
      //  form.setChannel("BAOFU");
        form.setBankCode("CMB");
        form.setCardNo("6226096551572609");
        form.setIdCardNo("441322198008140811");
        form.setRequestNo(SerialNumberUtils.generate());
        form.setMobile("13528822599");
        form.setUserId("135287");
        form.setUserName("陈道兴");
        BindSubmitResult result=bindService.submit(form);
        System.out.println(JsonUtils.toString(result));
    }
    
    @Test
    public void testBind(){
    	try {

		} catch (Exception e) {
			e.printStackTrace();
		};
    }
}
