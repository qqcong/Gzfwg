package com.bwoil.pay.gateway.service.impl;

import com.bwoil.pay.common.dao.AppDao;
import com.bwoil.pay.common.entity.App;
import com.bwoil.pay.gateway.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppDao appDao;
    @Override
    public App getApp(String appid) {
        return appDao.findById(appid);
    }
}
