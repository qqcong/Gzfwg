package com.bwoil.pay.gateway.controller;


import com.bwoil.pay.gateway.timer.ImportDataTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/api/fast/data")
public class ImportDataController {

    @Autowired
    private ImportDataTask task;

    @RequestMapping(value = "import",method = RequestMethod.GET)
    public String dataImport() {

        task.reImport();
        return "SUCCESS";
    }

    @RequestMapping(value = "test",method = RequestMethod.POST)
    public String test(HttpServletRequest request) {

        Enumeration<String>  params=request.getParameterNames();
        while(params.hasMoreElements()){
            String p=params.nextElement();
            System.out.println(p+"-->"+request.getParameter(p));
        }

        return "SUCCESS";
    }

}
