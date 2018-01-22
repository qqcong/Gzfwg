package com.bbbbb.pay.gateway.service.impl;

import com.bbbbb.pay.common.dao.AppDao;
import com.bbbbb.pay.common.entity.App;
import com.bbbbb.pay.gateway.service.AppService;
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
