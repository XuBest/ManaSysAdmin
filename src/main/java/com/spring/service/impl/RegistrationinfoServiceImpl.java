package com.spring.service.impl;

import com.base.ServiceBase;
import com.spring.dao.RegistrationinfoMapper;
import com.spring.entity.Registrationinfo;
import com.spring.service.RegistrationinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("RegistrationinfoService")
public class RegistrationinfoServiceImpl  extends ServiceBase<Registrationinfo> implements RegistrationinfoService {
    @Resource
    private RegistrationinfoMapper dao;

    @Override
    protected RegistrationinfoMapper getDao() {
        return  dao;
    }
}